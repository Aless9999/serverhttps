package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseCurrentFromWeatherServer(
        List<Weather> weather,
        Main main,
        Wind wind,
        long dt,
        String name
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            String description,
            String icon
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(
            double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("temp_min") double tempMin,
            @JsonProperty("temp_max") double tempMax,
            int humidity
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Wind(
            double speed,
            int deg
    ) {}
}
