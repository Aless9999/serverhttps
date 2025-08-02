package org.macnigor.serverhttps.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class WeatherNowResponse {
    private Main main;
    private Wind wind;
    private List<Weather> weather;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
        // можно добавить другие поля, если нужно
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Wind {
        private double speed;
        private int deg;  // Можно добавить поле градуса ветра, если понадобится
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Weather {
        private int id;            // добавлено
        private String main;       // добавлено
        private String description;
        private String icon;       // добавлено
    }
}
