package org.macnigor.serverhttps.bot.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.macnigor.serverhttps.model.WeatherNowResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class WeatherApp {

    @Value("${weather.api.key}")
    private String API_KEY;

    @Value("${weather.base.url}")
    private String BASE_URL;

    @Value("${weather.forecast.url}")
    private String FORECAST_URL;

    private static final Map<String, String> WEATHER_EMOJIS = Map.ofEntries(
            Map.entry("гроза", "⛈"),
            Map.entry("небольшой дождь", "🌧"),
            Map.entry("дождь", "🌧"),
            Map.entry("снег", "❄"),
            Map.entry("туман", "🌫"),
            Map.entry("ясно", "☀️"),
            Map.entry("облачно", "☁️"),
            Map.entry("переменная облачность", "🌤"),
            Map.entry("облачно с прояснениями", "🌤"),
            Map.entry("небольшая облачность", "🌤"),
            Map.entry("пасмурно", "☁️")
    );

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> choiceCity = new HashMap<>();

    public String getWeatherNow() {
        String city = getChoiceCity();
        try {
            String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric&lang=ru";
            String response = fetchResponse(urlString);
            WeatherNowResponse weather = objectMapper.readValue(response, WeatherNowResponse.class);

            return String.format(
                    "Сейчас в городе: %s\n\nТемпература: %.1f°C\nОщущается как: %.1f°C\nОписание: %s\nВлажность: %d%%\nСкорость ветра: %.1f м/с\n\n" +
                            "Сегодня ожидается:\n\n %s" +
                            "На ближайшие пять дней:\n\n %s",
                    city,
                    weather.getMain().getTemp(),
                    weather.getMain().getFeels_like(),
                    weather.getWeather().get(0).getDescription(),
                    weather.getMain().getHumidity(),
                    weather.getWind().getSpeed(),
                    getWeatherToday(),
                    getWeatherTomorrowAndNextFiveDays()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении данных о погоде.";
        }
    }

    public String getWeatherToday() {
        String jsonResponse = getFiveDayForecast();
        String description = "";
        String precipitationTime = "";

        double tempMax = Double.NEGATIVE_INFINITY;
        double tempMin = Double.POSITIVE_INFINITY;

        int clear = 0, cloudy = 0, overcast = 0, partlyCloudy = 0;
        boolean precipitationExpected = true;

        try {
            JsonNode listNode = objectMapper.readTree(jsonResponse).path("list");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            for (JsonNode weatherData : listNode) {
                String date = weatherData.path("dt_txt").asText().split(" ")[0];
                if (!date.equals(today)) break;

                double temp = weatherData.path("main").path("temp").asDouble();
                tempMax = Math.max(tempMax, temp);
                tempMin = Math.min(tempMin, temp);

                description = weatherData.path("weather").get(0).path("description").asText();

                if (Set.of("гроза", "дождь", "небольшой дождь", "снег").contains(description)) {
                    String time = weatherData.path("dt_txt").asText().split(" ")[1].substring(0, 5);
                    precipitationTime = " в " + time;
                    precipitationExpected = false;
                    break;
                }

                switch (description) {
                    case "ясно" -> clear++;
                    case "облачно" -> cloudy++;
                    case "пасмурно" -> overcast++;
                    default -> partlyCloudy++;
                }
            }

            if (precipitationExpected) {
                description = resolveDominantDescription(clear, cloudy, overcast, partlyCloudy, description);
            }

            return String.format("Температура: %.1f°C / %.1f°C\n%s %s%s",
                    tempMax, tempMin, getEmoji(description), description, precipitationTime
            );

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обработке прогноза на сегодня.", e);
        }
    }

    public String getWeatherTomorrowAndNextFiveDays() {
        String city = getChoiceCity();
        String jsonResponse = getFiveDayForecast();
        StringBuilder resultBuilder = new StringBuilder(city).append("\n");

        try {
            JsonNode listNode = objectMapper.readTree(jsonResponse).path("list");
            LocalDateTime now = LocalDateTime.now();

            for (int i = 1; i <= 5; i++) {
                double tempMax = Double.NEGATIVE_INFINITY;
                double tempMin = Double.POSITIVE_INFINITY;

                String description = "";
                String date = "";

                int clear = 0, cloudy = 0, overcast = 0, partlyCloudy = 0;
                boolean precipitationExpected = true;

                for (JsonNode weatherData : listNode) {
                    long dt = weatherData.path("dt").asLong();
                    LocalDateTime dateTime = convertToLocalDateTime(dt);

                    if (!dateTime.toLocalDate().equals(now.plusDays(i).toLocalDate())) continue;

                    date = weatherData.path("dt_txt").asText().split(" ")[0];
                    if (!precipitationExpected) continue;

                    description = weatherData.path("weather").get(0).path("description").asText();

                    switch (description) {
                        case "ясно" -> clear++;
                        case "облачно" -> cloudy++;
                        case "пасмурно" -> overcast++;
                        default -> partlyCloudy++;
                    }

                    double temp = weatherData.path("main").path("temp").asDouble();
                    tempMax = Math.max(tempMax, temp);
                    tempMin = Math.min(tempMin, temp);

                    if (Set.of("гроза", "дождь", "небольшой дождь", "снег").contains(description)) {
                        precipitationExpected = false;
                    }
                }

                if (precipitationExpected) {
                    description = resolveDominantDescription(clear, cloudy, overcast, partlyCloudy, description);
                }

                resultBuilder.append(String.format("%s %s %.1f°C макс./ %.1f°C мин.\n",
                        convertDateForDisplay(date), getEmoji(description), tempMax, tempMin));
            }

            return resultBuilder.toString();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении прогноза на 5 дней.", e);
        }
    }

    public String getFiveDayForecast() {
        String city = getChoiceCity();
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/forecast?lat=51.6720&lon=39.1843&appid=d6b80485c7ebcf208033a7ed332cb4af";
            return fetchResponse(urlString);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении данных о прогнозе погоды на 5 дней.", e);
        }
    }

    // --- Утилиты ---

    private String fetchResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().reduce("", (acc, line) -> acc + line);
        }
    }

    private String resolveDominantDescription(int clear, int cloudy, int overcast, int partlyCloudy, String fallback) {
        Map<String, Integer> count = Map.of(
                "ясно", clear,
                "облачно", cloudy,
                "пасмурно", overcast,
                "небольшая облачность", partlyCloudy
        );
        return count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .filter(e -> e.getValue() > 0)
                .map(Map.Entry::getKey)
                .orElse(fallback);
    }

    private String getEmoji(String description) {
        return WEATHER_EMOJIS.getOrDefault(description, "");
    }

    private String convertDateForDisplay(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.format(DateTimeFormatter.ofPattern("dd MMM", Locale.forLanguageTag("ru")));
    }

    private LocalDateTime convertToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    public String getChoiceCity() {
        return choiceCity.getOrDefault("Choice", "Voronezh");
    }

    public void setChoiceCity(String city) {
        choiceCity.clear();
        choiceCity.put("Choice", city);
    }
}
