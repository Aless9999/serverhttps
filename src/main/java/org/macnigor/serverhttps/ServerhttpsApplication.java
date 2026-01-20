package org.macnigor.serverhttps;

import org.macnigor.serverhttps.model.RegisterRequest;
import org.macnigor.serverhttps.model.RegisterResponse;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.model.dto.ResponseCurrentFromWeatherServer;
import org.macnigor.serverhttps.model.dto.ResponseForecastFromWeatherServer;
import org.macnigor.serverhttps.model.dto.WeatherProcessedResponse;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@RegisterReflectionForBinding(classes = {
        User.class,
        WeatherProcessedResponse.class,
        RegisterRequest.class,
        RegisterResponse.class,
        ResponseCurrentFromWeatherServer.class,
        ResponseCurrentFromWeatherServer.Weather.class,
        ResponseCurrentFromWeatherServer.Main.class,
        ResponseCurrentFromWeatherServer.Wind.class,
        ResponseForecastFromWeatherServer.class,
        ResponseForecastFromWeatherServer.ForecastItem.class,
        ResponseForecastFromWeatherServer.Main.class,
        ResponseForecastFromWeatherServer.Weather.class,
        ResponseForecastFromWeatherServer.Wind.class,
        ResponseForecastFromWeatherServer.City.class })
@SpringBootApplication
public class ServerhttpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerhttpsApplication.class, args);
	}

}
