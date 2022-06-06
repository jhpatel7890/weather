package com.weather.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
/** Configuration class which contains weather predicton output details */
public class WeatherPredictionConfiguration {
  @Value("${weather.prediction.days}")
  private Integer weatherPredictionDays;

  @Value("${weather.prediction.rain.notification}")
  private String weatherPredictionRainNotifiction;

  @Value("${weather.prediction.temp.notification}")
  private String weatherPredictionTempNotifiction;

  @Value("${weather.prediction.wind.notification}")
  private String weatherPredictionWindNotifiction;

  @Value("${weather.prediction.thunderstorm.notification}")
  private String weatherPredictionThunderstormNotifiction;
}
