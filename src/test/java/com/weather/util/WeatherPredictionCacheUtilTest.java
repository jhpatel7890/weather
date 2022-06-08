package com.weather.util;

import com.weather.pojo.WeatherPrediction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPredictionCacheUtilTest {
  @InjectMocks WeatherPredictionCacheUtil weatherPredictionCacheUtil;

  @Test
  public void updateAndGetCachedWeatherPredictionTest() {
    String city = "london";
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    weatherPrediction.setCode("200");
    weatherPredictionCacheUtil.updateWeatherPredictionCacheData(city, weatherPrediction);
    WeatherPrediction result = weatherPredictionCacheUtil.getWeatherPredictionCacheData(city);
    Assert.assertEquals("200", result.getCode());
  }
}
