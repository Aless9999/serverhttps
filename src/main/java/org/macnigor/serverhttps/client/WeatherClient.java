package org.macnigor.serverhttps.client;

import org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer;
import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    @Value("${weather.base.url}")
    private String baseUrl;

    @Value("${weather.forecast.url}")
    private String forecastUrl;

    private final RestTemplate restTemplate;

    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Запрос текущей погоды
    public ResponseCurrentFromWeatherServer fetchCurrentWeatherJson() {
        return restTemplate.getForObject(baseUrl, ResponseCurrentFromWeatherServer.class);
    }

    // Запрос прогноза на 5 дней
    public ResponseForecastFromWeatherServer fetchForecastJson() {
        return restTemplate.getForObject(forecastUrl, ResponseForecastFromWeatherServer.class);
    }
}
