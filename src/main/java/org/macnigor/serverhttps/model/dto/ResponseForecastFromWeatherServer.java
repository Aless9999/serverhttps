package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseForecastFromWeatherServer(
        List<ForecastItem> list,
        City city
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ForecastItem(
            long dt,
            Main main,
            List<Weather> weather,
            Wind wind
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("temp_min") double tempMin,
            @JsonProperty("temp_max") double tempMax,
            int humidity
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            String description,
            String icon
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Wind(
            double speed,
            int deg
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record City(
            String name
    ) {}
}
