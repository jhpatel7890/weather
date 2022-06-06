package com.weather.service;

import com.weather.exception.InternalServerErrorException;
import com.weather.processor.WeatherPredictionProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/** Factory class to determine object of WeatherPredictionProcessor */
@Component
public class WeatherPredictionProcessorFactory {
  @Autowired ApplicationContext applicationContext;

  private static final Logger logger =
      LogManager.getLogger(WeatherPredictionProcessorFactory.class);

  /**
   * Fetches the object of WeatherPredictionProcessor based on inputWeatherDataSource
   *
   * @param inputWeatherDataSource
   * @return
   * @throws InternalServerErrorException
   */
  public WeatherPredictionProcessor getWeatherPredictionProcessor(String inputWeatherDataSource)
      throws InternalServerErrorException {
    WeatherPredictionProcessor weatherPredictionProcessor = null;
    try {
      weatherPredictionProcessor =
          (WeatherPredictionProcessor) applicationContext.getBean(inputWeatherDataSource);
    } catch (NoSuchBeanDefinitionException exception) {
      exception.printStackTrace();
      throw new InternalServerErrorException(
          "Input weather data '" + inputWeatherDataSource + "' source is not supported");
    }
    return weatherPredictionProcessor;
  }
}
