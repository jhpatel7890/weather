package com.weather.service;

import com.weather.pojo.WeatherPrediction;

/** API Interface to predict weather for city */
public interface WeatherPredictionService {
  /**
   * Method to predict weather for given city and offlineMode
   *
   * @param city - String
   * @param offlineMode - String
   * @return WeatherPrediction object which has predicted weather
   * @throws Exception
   */
  WeatherPrediction getWeatherPrediction(String city, String offlineMode);
}
