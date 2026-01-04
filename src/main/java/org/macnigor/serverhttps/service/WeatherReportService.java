package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.springframework.stereotype.Component;

@Component
public class WeatherReportService {
    public String buildTextReport(WeatherProcessedResponse data) {
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
