package org.macnigor.serverhttps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherNowResponse {
    private Main main;
    private Wind wind;
    private List<Weather> weather;

    // вложенные классы
    @Getter
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
        // геттеры/сеттеры
    }

    @Getter
    public static class Wind {
        private double speed;
        // геттеры/сеттеры
    }

    @Getter
    public static class Weather {
        private String description;
        // геттеры/сеттеры
    }

    // геттеры/сеттеры
}

