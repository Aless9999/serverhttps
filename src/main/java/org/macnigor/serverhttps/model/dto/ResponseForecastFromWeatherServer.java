package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseForecastFromWeatherServer(
        @JsonProperty("list") List<ForecastItem> list,
        @JsonProperty("city") City city
) {

    @JsonCreator
    public ResponseForecastFromWeatherServer {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ForecastItem(
            @JsonProperty("dt") long dt,
            @JsonProperty("main") Main main,
            @JsonProperty("weather") List<Weather> weather,
            @JsonProperty("wind") Wind wind
    ) {
        @JsonCreator
        public ForecastItem {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("temp_min") double tempMin,
            @JsonProperty("temp_max") double tempMax,
            @JsonProperty("humidity") int humidity
    ) {
        @JsonCreator
        public Main {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            @JsonProperty("description") String description,
            @JsonProperty("icon") String icon
    ) {
        @JsonCreator
        public Weather {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Wind(
            @JsonProperty("speed") double speed,
            @JsonProperty("deg") int deg
    ) {
        @JsonCreator
        public Wind {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record City(
            @JsonProperty("name") String name
    ) {
        @JsonCreator
        public City {}
    }
}
