package org.macnigor.serverhttps.controller;

import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.macnigor.serverhttps.service.WeatherViewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherViewService weatherViewService;

    public WeatherController(WeatherViewService weatherViewService) {
        this.weatherViewService = weatherViewService;
    }

    @GetMapping("/processed")
    public WeatherProcessedResponse getProcessedWeather() {
        return weatherViewService.getProcessedJson();
    }

    @GetMapping(value = "/report", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String getWeatherReport() {
        return weatherViewService.getFormattedTextReport();
    }
}
