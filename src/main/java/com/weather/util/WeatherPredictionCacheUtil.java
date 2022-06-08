package com.weather.util;

import com.weather.pojo.WeatherPrediction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/** Util class to store WeatherPrediction per city in internal hashmap */
@Component
public class WeatherPredictionCacheUtil {
  // Hashmap to store WeatherPrediction for configured supported citys. Here we can use any other
  // caching mechanism like hazelcast, redis instead of using internal hashmap
  private Map<String, WeatherPrediction> weatherPredictionCachedData = new HashMap<>();

  /**
   * Method to return WeatherPrediction object for given city from internal hashmap
   *
   * @param city - String
   * @return WeatherPrediction
   */
  public WeatherPrediction getWeatherPredictionCacheData(String city) {
    return weatherPredictionCachedData.get(city);
  }

  /**
   * Method to update WeatherPrediction for given city in internal hashmap
   *
   * @param city - String
   * @param weatherPrediction - WeatherPrediction
   */
  public void updateWeatherPredictionCacheData(String city, WeatherPrediction weatherPrediction) {
    weatherPredictionCachedData.put(city, weatherPrediction);
  }
}
