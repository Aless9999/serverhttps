package org.macnigor.serverhttps.model.dto;

import java.util.List;

public record WeatherProcessedResponse(
        CurrentWeatherSummary now,
        List<DailyForecastSummary> dailyForecast
) {
    public record CurrentWeatherSummary(
            double temp,
            double feelsLike,
            String description,
            String emoji
    ) {}

    public record DailyForecastSummary(
            String date,
            double tempMax,
            double tempMin,
            String description,
            String emoji,
            String rainTime
    ) {}
}
