package com.weather.controller;

import com.weather.pojo.WeatherPrediction;
import com.weather.service.WeatherPredictionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPredictionControllerTest {
  @InjectMocks WeatherPredictionController weatherPredictionController;
  @Mock WeatherPredictionService weatherPredictionService;

  @Test
  public void weatherPredictionSuccessOfflineModeNoTest() throws Exception {
    String city = "Visnagar";
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    weatherPrediction.setCode("200");
    Mockito.doReturn(weatherPrediction)
        .when(weatherPredictionService)
        .getWeatherPrediction(city, "n");
    ResponseEntity<String> result =
        weatherPredictionController.predictWeatherForNext3Days(city, "n");
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.getBody());
    Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  public void weatherPredictionSuccessOfflineModeYesTest() throws Exception {
    String city = "Visnagar";
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    weatherPrediction.setCode("200");
    Mockito.doReturn(weatherPrediction)
        .when(weatherPredictionService)
        .getWeatherPrediction(city, "y");
    ResponseEntity<String> result =
        weatherPredictionController.predictWeatherForNext3Days(city, "y");
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.getBody());
    Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  public void weatherPredictionCityNotFoundTest() throws Exception {
    String city = "Visnagar";
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    weatherPrediction.setCode("404");
    Mockito.doReturn(weatherPrediction)
        .when(weatherPredictionService)
        .getWeatherPrediction(city, "n");
    ResponseEntity<String> result =
        weatherPredictionController.predictWeatherForNext3Days(city, "n");
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.getBody());
    Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void weatherPredictionInternalServerExceptionTest() throws Exception {
    String city = "Visnagar";
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    weatherPrediction.setCode("500");
    Mockito.doReturn(weatherPrediction)
        .when(weatherPredictionService)
        .getWeatherPrediction(city, "n");
    ResponseEntity<String> result =
        weatherPredictionController.predictWeatherForNext3Days(city, "n");
    Assert.assertNotNull(result);
    Assert.assertNotNull(result.getBody());
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
  }
}
