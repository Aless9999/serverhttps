package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer;
import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.springframework.stereotype.Component;
import org.macnigor.serverhttps.service.util.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMappingService {
    private final WeatherProcessor processor;
    private final org.macnigor.serverhttps.util.WeatherFormatter formatter;
    public WeatherMappingService(WeatherProcessor processor, org.macnigor.serverhttps.util.WeatherFormatter formatter) {
        this.processor = processor;
        this.formatter = formatter;
    }

    public WeatherProcessedResponse mapToProcessedResponse(ResponseCurrentFromWeatherServer current,
                                                           ResponseForecastFromWeatherServer forecast) {
        var now = new WeatherProcessedResponse.CurrentWeatherSummary(
                current.main().temp(),
                current.main().feelsLike(),
                current.weather().getFirst().description(),
                formatter.getEmoji(current.weather().getFirst().description())
        );

        var daysMap = processor.groupByDay(forecast);
        LocalDate today = LocalDate.now();

        List<WeatherProcessedResponse.DailyForecastSummary> daily = daysMap.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(today))
                .limit(6)
                .map(entry -> {
                    var items = entry.getValue();
                    String desc = items.get(items.size() / 2).weather().getFirst().description();
                    String rainTime = processor.findFirstPrecipitation(items)
                            .map(p -> formatter.formatTime(p.dt()))
                            .orElse(null);

                    return new WeatherProcessedResponse.DailyForecastSummary(
                            formatter.formatDate(entry.getKey()),
                            processor.getMaxTemp(items),
                            processor.getMinTemp(items),
                            desc,
                            formatter.getEmoji(desc),
                            rainTime
                    );
                }).collect(Collectors.toList());

        return new WeatherProcessedResponse(now, daily);
    }
}