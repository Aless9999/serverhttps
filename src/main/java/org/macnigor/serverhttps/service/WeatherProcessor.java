package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeatherProcessor {

    public Map<LocalDate, List<ResponseForecastFromWeatherServer.ForecastItem>> groupByDay(ResponseForecastFromWeatherServer forecast) {
        return forecast.list().stream()
                .collect(Collectors.groupingBy(
                        item -> Instant.ofEpochSecond(item.dt()).atZone(ZoneId.systemDefault()).toLocalDate(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public double getMaxTemp(List<ResponseForecastFromWeatherServer.ForecastItem> items) {
        return items.stream().mapToDouble(i -> i.main().tempMax()).max().orElse(0);
    }

    public double getMinTemp(List<ResponseForecastFromWeatherServer.ForecastItem> items) {
        return items.stream().mapToDouble(i -> i.main().tempMin()).min().orElse(0);
    }

    public Optional<ResponseForecastFromWeatherServer.ForecastItem> findFirstPrecipitation(List<ResponseForecastFromWeatherServer.ForecastItem> items) {
        return items.stream()
                .filter(i -> {
                    String d = i.weather().get(0).description().toLowerCase();
                    return d.contains("дождь") || d.contains("снег") || d.contains("гроза");
                })
                .findFirst();
    }
}
