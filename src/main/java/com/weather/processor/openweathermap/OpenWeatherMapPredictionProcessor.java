package com.weather.processor.openweathermap;

import com.weather.pojo.WeatherPrediction;
import com.weather.processor.WeatherPredictionProcessor;
import com.weather.processor.openweathermap.input.WeatherForcast;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class implementation to predict weather using openweathermap source */
@Component("openweathermap")
public class OpenWeatherMapPredictionProcessor implements WeatherPredictionProcessor {
  @Autowired OpenWeatherMapFetchData openWeatherMapFetchData;
  @Autowired OpenWeatherMapProcessData openWeatherMapProcessData;
  @Autowired OpenWeatherMapExceptionHandler openWeatherMapExceptionHandler;
  private static final Logger logger =
      LogManager.getLogger(OpenWeatherMapPredictionProcessor.class);

  /**
   * Fetches weather forcast data using openweathermap api and process the collected data and
   * returns the object of WeatherPrediction class
   *
   * @param city - String
   * @return weatherPrediction - WeatherPrediction
   * @throws Exception
   */
  @Override
  public WeatherPrediction predictWeather(String city) throws Exception {
    WeatherPrediction weatherPrediction = null;
    try {
      WeatherForcast weatherForcast = openWeatherMapFetchData.fetchWeatherForcastData(city);
      weatherPrediction = openWeatherMapProcessData.processData(weatherForcast);
    } catch (Exception exception) {
      openWeatherMapExceptionHandler.handleExceptions(exception, city);
    }
    return weatherPrediction;
  }
}
