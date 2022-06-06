package com.weather.processor.openweathermap;

import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import com.weather.pojo.WeatherPrediction;
import com.weather.processor.openweathermap.input.WeatherForcast;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapPredictionProcessorTest {
  @InjectMocks OpenWeatherMapPredictionProcessor openWeatherMapPredictionProcessor;
  @Mock OpenWeatherMapFetchData openWeatherMapFetchData;
  @Mock OpenWeatherMapProcessData openWeatherMapProcessData;
  @Mock WeatherPrediction weatherPrediction;
  @Mock WeatherForcast weatherForcast;

  @Test
  public void predictWeatherTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn(weatherForcast).when(openWeatherMapFetchData).fetchWeatherForcastData(city);
    Mockito.doReturn(weatherPrediction).when(openWeatherMapProcessData).processData(weatherForcast);
    WeatherPrediction result = openWeatherMapPredictionProcessor.predictWeather(city);
    Assert.assertNotNull(result);
  }

  @Test(expected = CityNotFoundException.class)
  public void getWeatherPredictionTestCityNotFound() throws Exception {
    String city = "dummy";
    Mockito.doThrow(new CityNotFoundException("City 'dummy' not found"))
        .when(openWeatherMapFetchData)
        .fetchWeatherForcastData(city);
    openWeatherMapPredictionProcessor.predictWeather(city);
  }

  @Test(expected = InternalServerErrorException.class)
  public void getWeatherPredictionTestInternalServerException() throws Exception {
    String city = "dummy";
    Mockito.doThrow(new InternalServerErrorException("Invalid openweathermap api id"))
        .when(openWeatherMapFetchData)
        .fetchWeatherForcastData(city);
    openWeatherMapPredictionProcessor.predictWeather(city);
  }
}
