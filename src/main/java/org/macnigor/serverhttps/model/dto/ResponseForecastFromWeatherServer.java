package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.util.List;


@RegisterReflectionForBinding(classes = {
        ResponseForecastFromWeatherServer.class,
        ResponseForecastFromWeatherServer.ForecastItem.class,
        ResponseForecastFromWeatherServer.Main.class,
        ResponseForecastFromWeatherServer.Weather.class,
        ResponseForecastFromWeatherServer.Wind.class,
        ResponseForecastFromWeatherServer.City.class
})
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseForecastFromWeatherServer(
        @JsonProperty("list") List<ForecastItem> list,
        @JsonProperty("city") City city
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ForecastItem(
            @JsonProperty("dt") long dt,
            @JsonProperty("main") Main main,
            @JsonProperty("weather") List<Weather> weather,
            @JsonProperty("wind") Wind wind
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("temp_min") double tempMin,
            @JsonProperty("temp_max") double tempMax,
            @JsonProperty("humidity") int humidity
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            @JsonProperty("description") String description,
            @JsonProperty("icon") String icon
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Wind(
            @JsonProperty("speed") double speed,
            @JsonProperty("deg") int deg
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record City(
            @JsonProperty("name") String name
    ) {}
}