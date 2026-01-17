package org.macnigor.serverhttps.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.util.List;


@RegisterReflectionForBinding(classes = {
        ResponseCurrentFromWeatherServer.class,
        ResponseCurrentFromWeatherServer.Weather.class,
        ResponseCurrentFromWeatherServer.Main.class,
        ResponseCurrentFromWeatherServer.Wind.class
})
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseCurrentFromWeatherServer(
        @JsonProperty("weather") List<Weather> weather,
        @JsonProperty("main") Main main,
        @JsonProperty("wind") Wind wind,
        @JsonProperty("dt") long dt,
        @JsonProperty("name") String name
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            @JsonProperty("description") String description,
            @JsonProperty("icon") String icon
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
    public record Wind(
            @JsonProperty("speed") double speed,
            @JsonProperty("deg") int deg
    ) {}
}