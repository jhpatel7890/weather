package com.weather.service;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import com.weather.pojo.WeatherPrediction;
import com.weather.processor.WeatherPredictionProcessor;
import com.weather.util.WeatherPredictionCacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class implementation for weather prediction service */
@Component
public class WeatherPredictionServiceImpl implements WeatherPredictionService {

  @Autowired WeatherPredictionProcessorFactory weatherPredictionProcessorFactory;
  @Autowired WeatherInputConfiguration weatherInputConfiguration;
  @Autowired WeatherPredictionCacheUtil weatherPredictionCacheUtil;
  private static final Logger logger = LogManager.getLogger(WeatherPredictionServiceImpl.class);

  /**
   * Based on configured input weather data source, it fetches WeatherPredictionProcessor and then
   * calls respective predictWeather method which returns object of WeatherPrediction as output and
   * it also handles various exception raised during weather prediction process. If offlineMode is
   * on then it fetches weatherPrediction from internally stored hashmap
   *
   * @param city - String
   * @param offlineMode - String
   * @return WeatherPrediction object which has predicted weather
   * @throws Exception
   */
  @Override
  public WeatherPrediction getWeatherPrediction(String city, String offlineMode) {
    WeatherPrediction weatherPrediction = null;
    logger.info("-- getWeatherPrediction city {} offlineMode {}", city, offlineMode);
    try {
      if (isOfflineModeOn(offlineMode)) {
        logger.info("-- getWeatherPrediction Fetching weatherPrediction from cached data");
        weatherPrediction = weatherPredictionCacheUtil.getWeatherPredictionCacheData(city);
      }
      if (weatherPrediction == null) {
        logger.info(
            "-- getWeatherPrediction Calculating weatherPrediction from weather source data");
        WeatherPredictionProcessor weatherPredictionProcessor =
            weatherPredictionProcessorFactory.getWeatherPredictionProcessor(
                weatherInputConfiguration.getInputWeatherDataSource());
        weatherPrediction = weatherPredictionProcessor.predictWeather(city);
      }
      weatherPrediction.setCode("200");
      weatherPrediction.setMessage("Success");
    } catch (Exception exception) {
      weatherPrediction = handleException(exception);
    }
    logger.info("-- getWeatherPrediction weatherPrediction {}", weatherPrediction);
    return weatherPrediction;
  }

  /**
   * Checks whether offline mode is on or not
   *
   * @param offlineMode - String
   * @return boolean value which indicates whethere offline mode is on or not
   */
  private boolean isOfflineModeOn(String offlineMode) {
    return offlineMode.equals("y") || offlineMode.equals("yes");
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
      weatherPrediction.setCode("404");
      weatherPrediction.setMessage(exception.getMessage());
    } else if (exception instanceof InternalServerErrorException) {
      weatherPrediction.setCode("500");
      weatherPrediction.setMessage(exception.getMessage());
    } else {
      weatherPrediction.setCode("500");
      weatherPrediction.setMessage(exception.getMessage());
    }
    return weatherPrediction;
  }
}
