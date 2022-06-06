package com.weather.service;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import com.weather.pojo.WeatherPrediction;
import com.weather.processor.WeatherPredictionProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class implementation for weather prediction service */
@Component
public class WeatherPredictionServiceImpl implements WeatherPredictionService {

  @Autowired WeatherPredictionProcessorFactory weatherPredictionProcessorFactory;
  @Autowired WeatherInputConfiguration weatherInputConfiguration;
  private static final Logger logger = LogManager.getLogger(WeatherPredictionServiceImpl.class);

  /**
   * Based on configured input weather data source, it fetches WeatherPredictionProcessor and then
   * calls respective predictWeather method which returns object of WeatherPrediction as output and
   * it also handles various exception raised during weather prediction process.
   *
   * @param city - String
   * @return WeatherPrediction object which has predicted weather
   * @throws Exception
   */
  @Override
  public WeatherPrediction getWeatherPrediction(String city) {
    WeatherPrediction weatherPrediction = null;
    try {
      WeatherPredictionProcessor weatherPredictionProcessor =
          weatherPredictionProcessorFactory.getWeatherPredictionProcessor(
              weatherInputConfiguration.getInputWeatherDataSource());
      weatherPrediction = weatherPredictionProcessor.predictWeather(city);
      weatherPrediction.setCode("200");
      weatherPrediction.setMessage("Success");
    } catch (Exception exception) {
      weatherPrediction = handleException(exception);
    }
    return weatherPrediction;
  }

  /**
   * Handles various exceptions and set code and message accordingly and returns object of
   * WeatherPrediction
   *
   * @param exception - Raised Exception
   * @return weatherPrediction - WeatherPrediction
   */
  private WeatherPrediction handleException(Exception exception) {
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    if (exception instanceof CityNotFoundException) {
      weatherPrediction = new WeatherPrediction();
      weatherPrediction.setCode("404");
      weatherPrediction.setMessage(exception.getMessage());
    } else if (exception instanceof InternalServerErrorException) {
      weatherPrediction = new WeatherPrediction();
      weatherPrediction.setCode("500");
      weatherPrediction.setMessage(exception.getMessage());
    } else {
      weatherPrediction = new WeatherPrediction();
      weatherPrediction.setCode("500");
      weatherPrediction.setMessage(exception.getMessage());
    }
    return weatherPrediction;
  }
}
