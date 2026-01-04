package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer;
import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.macnigor.serverhttps.util.WeatherFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherViewService {

    private final WeatherService weatherService;
    private final WeatherProcessor processor;
    private final WeatherFormatter formatter;

    public WeatherViewService(WeatherService weatherService,
                              WeatherProcessor processor,
                              WeatherFormatter formatter) {
        this.weatherService = weatherService;
        this.processor = processor;
        this.formatter = formatter;
    }

    // 1. Метод для получения готового JSON (для Android)
    public WeatherProcessedResponse getProcessedJson() {
        ResponseCurrentFromWeatherServer currentRaw = weatherService.getCurrent();
        ResponseForecastFromWeatherServer forecastRaw = weatherService.getForecast();
        return buildJsonResponse(currentRaw, forecastRaw);
    }

    // 2. Метод для получения текста (использует данные из JSON метода)
    public String getFormattedTextReport() {
        WeatherProcessedResponse data = getProcessedJson();
        return buildTextString(data);
    }

    private WeatherProcessedResponse buildJsonResponse(ResponseCurrentFromWeatherServer current,
                                                       ResponseForecastFromWeatherServer forecast) {
        // Формируем блок "Сейчас"
        var now = new WeatherProcessedResponse.CurrentWeatherSummary(
                current.main().temp(),
                current.main().feelsLike(),
                current.weather().get(0).description(),
                formatter.getEmoji(current.weather().get(0).description())
        );

        // Формируем блок "Прогноз"
        var daysMap = processor.groupByDay(forecast);
        LocalDate today = LocalDate.now();

        List<WeatherProcessedResponse.DailyForecastSummary> daily = daysMap.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(today)) // Оставляем сегодня и будущее
                .limit(6)
                .map(entry -> {
                    var items = entry.getValue();
                    String desc = items.get(items.size() / 2).weather().get(0).description();

                    String rainTime = processor.findFirstPrecipitation(items)
                            .map(p -> formatter.formatTime(p.dt()))
                            .orElse(null);

                    return new WeatherProcessedResponse.DailyForecastSummary(
                            formatter.formatDate(entry.getKey()),
                            processor.getMaxTemp(items),
                            processor.getMinTemp(items),
                            desc,
                            formatter.getEmoji(desc),
                            rainTime
                    );
                }).collect(Collectors.toList());

        return new WeatherProcessedResponse(now, daily);
    }

    private String buildTextString(WeatherProcessedResponse data) {
        StringBuilder sb = new StringBuilder();
        sb.append("📍 Воронеж: сейчас\n")
                .append(String.format("%s %s, %.1f°C (ощущается как %.1f°C)\n",
                        data.now().emoji(), data.now().description(), data.now().temp(), data.now().feelsLike()))
                .append("----------------------------\n");

        for (var day : data.dailyForecast()) {
            sb.append(String.format("%s: %.1f° / %.1f° %s %s",
                    day.date(), day.tempMax(), day.tempMin(), day.emoji(), day.description()));

            if (day.rainTime() != null) {
                sb.append(" (осадки в ").append(day.rainTime()).append(")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}