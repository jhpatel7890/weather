package com.weather.service;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import com.weather.pojo.WeatherPrediction;
import com.weather.processor.openweathermap.OpenWeatherMapPredictionProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPredictionServiceImplTest {
  @InjectMocks WeatherPredictionServiceImpl weatherPredictionServiceImpl;
  @Mock WeatherPredictionProcessorFactory weatherPredictionProcessorFactory;
  @Mock WeatherInputConfiguration weatherInputConfiguration;
  @Mock OpenWeatherMapPredictionProcessor openWeatherMapPredictionProcessor;

  @Test
  public void getWeatherPredictionTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("openweathermap").when(weatherInputConfiguration).getInputWeatherDataSource();
    Mockito.doReturn(openWeatherMapPredictionProcessor)
        .when(weatherPredictionProcessorFactory)
        .getWeatherPredictionProcessor("openweathermap");
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    Mockito.doReturn(weatherPrediction)
        .when(openWeatherMapPredictionProcessor)
        .predictWeather(city);
    WeatherPrediction result = weatherPredictionServiceImpl.getWeatherPrediction(city);
    Assert.assertNotNull(result);
    Assert.assertEquals("200", result.getCode());
    Assert.assertEquals("Success", result.getMessage());
  }

  @Test
  public void getWeatherPredictionCityNotFoundTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("openweathermap").when(weatherInputConfiguration).getInputWeatherDataSource();
    Mockito.doReturn(openWeatherMapPredictionProcessor)
        .when(weatherPredictionProcessorFactory)
        .getWeatherPredictionProcessor("openweathermap");
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    Mockito.doThrow(new CityNotFoundException("City '" + city + "' not found"))
        .when(openWeatherMapPredictionProcessor)
        .predictWeather(city);
    WeatherPrediction result = weatherPredictionServiceImpl.getWeatherPrediction(city);
    Assert.assertNotNull(result);
    Assert.assertEquals("404", result.getCode());
    Assert.assertEquals("City '" + city + "' not found", result.getMessage());
  }

  @Test
  public void getWeatherPredictionInternalServerErrorTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("openweathermap").when(weatherInputConfiguration).getInputWeatherDataSource();
    Mockito.doReturn(openWeatherMapPredictionProcessor)
        .when(weatherPredictionProcessorFactory)
        .getWeatherPredictionProcessor("openweathermap");
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    Mockito.doThrow(new InternalServerErrorException("Invalid openweathermap Api Id"))
        .when(openWeatherMapPredictionProcessor)
        .predictWeather(city);
    WeatherPrediction result = weatherPredictionServiceImpl.getWeatherPrediction(city);
    Assert.assertNotNull(result);
    Assert.assertEquals("500", result.getCode());
    Assert.assertEquals("Invalid openweathermap Api Id", result.getMessage());
  }

  @Test
  public void getWeatherPredictionUnhandledExceptionTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("openweathermap").when(weatherInputConfiguration).getInputWeatherDataSource();
    Mockito.doReturn(openWeatherMapPredictionProcessor)
        .when(weatherPredictionProcessorFactory)
        .getWeatherPredictionProcessor("openweathermap");
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    Mockito.doThrow(new Exception("Unhandled exception"))
        .when(openWeatherMapPredictionProcessor)
        .predictWeather(city);
    WeatherPrediction result = weatherPredictionServiceImpl.getWeatherPrediction(city);
    Assert.assertNotNull(result);
    Assert.assertEquals("500", result.getCode());
    Assert.assertEquals("Unhandled exception", result.getMessage());
  }
}
