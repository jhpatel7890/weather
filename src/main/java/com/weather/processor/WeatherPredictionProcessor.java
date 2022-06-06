package com.weather.processor;

import com.weather.pojo.WeatherPrediction;

/** Interface class to predict weather */
public interface WeatherPredictionProcessor {
  WeatherPrediction predictWeather(String city) throws Exception;
}
