package com.weather.processor.openweathermap;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import com.weather.processor.openweathermap.input.WeatherForcast;
import java.io.File;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapFetchDataTest {
  @InjectMocks OpenWeatherMapFetchData openWeatherMapFetchData;
  @Mock WeatherInputConfiguration weatherInputConfiguration;
  @Mock OpenWeatherMapCallApi openWeatherMapCallApi;
  @Mock OpenWeatherMapExceptionHandler openWeatherMapExceptionHandler;

  @Test
  public void predictWeatherTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("http://api.openweathermap.org/data/2.5/forecast")
        .when(weatherInputConfiguration)
        .getInputWeatherDataUrl();
    Mockito.doReturn("metric").when(weatherInputConfiguration).getInputWeatherDataUnits();
    Mockito.doReturn("SampleAppId").when(weatherInputConfiguration).getOpenWeatherAppId();
    File resource = new ClassPathResource("testdata/openWeatherMapSuccessData.json").getFile();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));

    Mockito.doReturn(fetchedWeatherData)
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            "http://api.openweathermap.org/data/2.5/forecast?q=Visnagar&mode=json&units=metric&appid=SampleAppId");
    WeatherForcast result = openWeatherMapFetchData.fetchWeatherForcastData(city);
    Assert.assertNotNull(result);
  }

  @Test(expected = CityNotFoundException.class)
  public void predictWeatherCityNotFoundExceptionTest() throws Exception {
    String city = "dummy";
    Mockito.doReturn("http://api.openweathermap.org/data/2.5/forecast")
        .when(weatherInputConfiguration)
        .getInputWeatherDataUrl();
    Mockito.doReturn("metric").when(weatherInputConfiguration).getInputWeatherDataUnits();
    Mockito.doReturn("SampleAppId").when(weatherInputConfiguration).getOpenWeatherAppId();
    Mockito.doThrow(new CityNotFoundException(""))
        .when(openWeatherMapExceptionHandler)
        .handleExceptions(Mockito.any(HttpClientErrorException.class), Mockito.eq(city));
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            "http://api.openweathermap.org/data/2.5/forecast?q=dummy&mode=json&units=metric&appid=SampleAppId");
    WeatherForcast result = openWeatherMapFetchData.fetchWeatherForcastData(city);
  }

  @Test(expected = InternalServerErrorException.class)
  public void predictWeatherUnauthorisedExceptionTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("http://api.openweathermap.org/data/2.5/forecast")
        .when(weatherInputConfiguration)
        .getInputWeatherDataUrl();
    Mockito.doReturn("metric").when(weatherInputConfiguration).getInputWeatherDataUnits();
    Mockito.doReturn("InvalidAppId").when(weatherInputConfiguration).getOpenWeatherAppId();
    Mockito.doThrow(new InternalServerErrorException(""))
        .when(openWeatherMapExceptionHandler)
        .handleExceptions(Mockito.any(HttpClientErrorException.class), Mockito.eq(city));
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED))
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            "http://api.openweathermap.org/data/2.5/forecast?q=Visnagar&mode=json&units=metric&appid=InvalidAppId");
    WeatherForcast result = openWeatherMapFetchData.fetchWeatherForcastData(city);
  }

  @Test(expected = InternalServerErrorException.class)
  public void predictWeatherResourceAccessExceptionTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn("http://api.openweathermap.org/data/2.5/forecast")
        .when(weatherInputConfiguration)
        .getInputWeatherDataUrl();
    Mockito.doReturn("metric").when(weatherInputConfiguration).getInputWeatherDataUnits();
    Mockito.doReturn("SampleAppId").when(weatherInputConfiguration).getOpenWeatherAppId();
    Mockito.doThrow(new InternalServerErrorException(""))
        .when(openWeatherMapExceptionHandler)
        .handleExceptions(Mockito.any(ResourceAccessException.class), Mockito.eq(city));
    Mockito.doThrow(new ResourceAccessException(""))
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            "http://api.openweathermap.org/data/2.5/forecast?q=Visnagar&mode=json&units=metric&appid=SampleAppId");
    WeatherForcast result = openWeatherMapFetchData.fetchWeatherForcastData(city);
  }
}
