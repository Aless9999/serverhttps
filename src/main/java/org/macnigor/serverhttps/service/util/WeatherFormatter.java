package org.macnigor.serverhttps.util;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Component
public class WeatherFormatter {

    private static final Map<String, String> WEATHER_EMOJIS = Map.of(
            "ясно", "☀️", "облачно", "☁️", "пасмурно", "☁️",
            "небольшой дождь", "🌧", "дождь", "🌧", "гроза", "⛈", "снег", "❄️"
    );

    public String getEmoji(String desc) {
        return WEATHER_EMOJIS.getOrDefault(desc.toLowerCase(), "🌡");
    }

    public String formatTime(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault()).toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd MMM", new Locale("ru")));
    }
}
