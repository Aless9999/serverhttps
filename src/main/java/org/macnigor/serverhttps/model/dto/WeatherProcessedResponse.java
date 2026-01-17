package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.util.List;

@RegisterReflectionForBinding({
        WeatherProcessedResponse.class,
        WeatherProcessedResponse.CurrentWeatherSummary.class,
        WeatherProcessedResponse.DailyForecastSummary.class,
        org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer.Wind.class
})
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherProcessedResponse(
        CurrentWeatherSummary now,
        List<DailyForecastSummary> dailyForecast
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CurrentWeatherSummary(
            double temp,
            double feelsLike,
            String description,
            String emoji,
            ResponseCurrentFromWeatherServer.Wind wind

    ) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DailyForecastSummary(
            String date,
            double tempMax,
            double tempMin,
            String description,
            String emoji,
            String rainTime
    ) {}
}
