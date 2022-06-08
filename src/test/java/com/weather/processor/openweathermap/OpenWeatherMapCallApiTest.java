package com.weather.processor.openweathermap;

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
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapCallApiTest {
  @InjectMocks OpenWeatherMapCallApi openWeatherMapCallApi;
  @Mock RestTemplate restTemplate;

  @Test
  public void getOpenWeatherMapRestDataTest() throws Exception {
    String city = "Visnagar";
    String openWeatherMapUrl =
        "http://api.openweathermap.org/data/2.5/forecast?q="
            + city
            + "&mode=json&units=metric&appid=SampleAppId";
    File resource = new ClassPathResource("testdata/openWeatherMapSuccessData.json").getFile();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    Mockito.doReturn(fetchedWeatherData)
        .when(restTemplate)
        .getForObject(openWeatherMapUrl, String.class);
    String result = openWeatherMapCallApi.getOpenWeatherMapRestData(openWeatherMapUrl);
    Assert.assertNotNull(result);
  }

  @Test(expected = HttpClientErrorException.class)
  public void getOpenWeatherMapRestDataCityNotFoundTest() throws Exception {
    String city = "dummy";
    String openWeatherMapUrl =
        "http://api.openweathermap.org/data/2.5/forecast?q="
            + city
            + "&mode=json&units=metric&appid=SampleAppId";
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
        .when(restTemplate)
        .getForObject(openWeatherMapUrl, String.class);
    openWeatherMapCallApi.getOpenWeatherMapRestData(openWeatherMapUrl);
  }

  @Test(expected = HttpClientErrorException.class)
  public void getOpenWeatherMapRestDataUnauthorisedExceptionTest() throws Exception {
    String city = "Visnagar";
    String openWeatherMapUrl =
        "http://api.openweathermap.org/data/2.5/forecast?q="
            + city
            + "&mode=json&units=metric&appid=SampleAppId";
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED))
        .when(restTemplate)
        .getForObject(openWeatherMapUrl, String.class);
    openWeatherMapCallApi.getOpenWeatherMapRestData(openWeatherMapUrl);
  }

  @Test(expected = ResourceAccessException.class)
  public void getOpenWeatherMapRestDataCityResourceAccessExceptionTest() throws Exception {
    String city = "Visnagar";
    String openWeatherMapUrl =
        "http://api.openweathermap.org/data/2.5/forecast?q="
            + city
            + "&mode=json&units=metric&appid=SampleAppId";
    Mockito.doThrow(new ResourceAccessException(""))
        .when(restTemplate)
        .getForObject(openWeatherMapUrl, String.class);
    openWeatherMapCallApi.getOpenWeatherMapRestData(openWeatherMapUrl);
  }
}
