package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherProcessedResponse(
        @JsonProperty("now") CurrentWeatherSummary now,
        @JsonProperty("dailyForecast") List<DailyForecastSummary> dailyForecast
) {

    @JsonCreator
    public WeatherProcessedResponse {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CurrentWeatherSummary(
            @JsonProperty("temp") double temp,
            @JsonProperty("feelsLike") double feelsLike,
            @JsonProperty("description") String description,
            @JsonProperty("emoji") String emoji,
            @JsonProperty("wind")
            org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer.Wind wind
    ) {
        @JsonCreator
        public CurrentWeatherSummary {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DailyForecastSummary(
            @JsonProperty("date") String date,
            @JsonProperty("tempMax") double tempMax,
            @JsonProperty("tempMin") double tempMin,
            @JsonProperty("description") String description,
            @JsonProperty("emoji") String emoji,
            @JsonProperty("rainTime") String rainTime
    ) {
        @JsonCreator
        public DailyForecastSummary {}
    }
}
