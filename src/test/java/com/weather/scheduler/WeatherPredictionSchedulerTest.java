package com.weather.scheduler;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.pojo.WeatherPrediction;
import com.weather.service.WeatherPredictionService;
import com.weather.util.WeatherPredictionCacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPredictionSchedulerTest {
  @InjectMocks WeatherPredictionScheduler weatherPredictionScheduler;
  @Mock WeatherInputConfiguration weatherInputConfiguration;
  @Mock WeatherPredictionService weatherPredictionService;
  @Mock WeatherPredictionCacheUtil weatherPredictionCacheUtil;

  @Test
  public void collectWeatherPredictionDataForSupportedCitysTest() throws Exception {
    Mockito.doReturn("London,Visnagar,Dummy")
        .when(weatherInputConfiguration)
        .getWeatherDataOfflineSupportedCitys();
    WeatherPrediction weatherPredictionLondon = new WeatherPrediction();
    weatherPredictionLondon.setCode("200");
    Mockito.doReturn(weatherPredictionLondon)
        .when(weatherPredictionService)
        .getWeatherPrediction("london", "n");
    WeatherPrediction weatherPredictionVisnagar = new WeatherPrediction();
    weatherPredictionVisnagar.setCode("200");
    Mockito.doReturn(weatherPredictionVisnagar)
        .when(weatherPredictionService)
        .getWeatherPrediction("visnagar", "n");
    WeatherPrediction weatherPredictionDummy = new WeatherPrediction();
    weatherPredictionDummy.setCode("404");
    Mockito.doReturn(weatherPredictionDummy)
        .when(weatherPredictionService)
        .getWeatherPrediction("dummy", "n");
    weatherPredictionScheduler.collectWeatherPredictionDataForSupportedCitys();
    Mockito.verify(weatherPredictionCacheUtil, Mockito.times(2))
        .updateWeatherPredictionCacheData(
            Mockito.any(String.class), Mockito.any(WeatherPrediction.class));
  }
}
