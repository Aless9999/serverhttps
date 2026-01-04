package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherViewService {
    private final WeatherService weatherService;
    private final WeatherMappingService mappingService;
    private final WeatherReportService reportService;

    public WeatherViewService(WeatherService weatherService,
                              WeatherMappingService mappingService,
                              WeatherReportService reportService) {
        this.weatherService = weatherService;
        this.mappingService = mappingService;
        this.reportService = reportService;
    }

    public WeatherProcessedResponse getProcessedJson() {
        return mappingService.mapToProcessedResponse(
                weatherService.getCurrent(),
                weatherService.getForecast()
        );
    }

    public String getFormattedTextReport() {
        return reportService.buildTextReport(getProcessedJson());
    }
}