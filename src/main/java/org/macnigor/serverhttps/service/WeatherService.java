package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.client.WeatherClient;
import org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer;
import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }


    public ResponseCurrentFromWeatherServer getCurrent() {
        return weatherClient.fetchCurrentWeatherJson();
    }


    public ResponseForecastFromWeatherServer getForecast() {
        return weatherClient.fetchForecastJson();
    }
}
