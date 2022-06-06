package com.weather.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
/** Configuration class which contains input weather data details */
public class WeatherInputConfiguration {
  @Value("${input.weather.data.source}")
  private String inputWeatherDataSource;

  @Value("${OPEN_WEATHER_MAP_APP_ID}")
  private String openWeatherAppId;

  @Value("${input.weather.data.url}")
  private String inputWeatherDataUrl;

  @Value("${input.weather.data.units}")
  private String inputWeatherDataUnits;

  @Value("${input.weather.data.threshold.max.temp}")
  private Double inputWeatherDataThresholdMaxTemp;

  @Value("${input.weather.data.threshold.max.wind.speed}")
  private Integer inputWeatherDataThresholdMaxWindSpeed;
}
