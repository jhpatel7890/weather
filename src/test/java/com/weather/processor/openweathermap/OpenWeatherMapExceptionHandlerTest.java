package com.weather.processor.openweathermap;

import com.google.gson.JsonSyntaxException;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapExceptionHandlerTest {
  @InjectMocks OpenWeatherMapExceptionHandler openWeatherMapExceptionHandler;

  @Test(expected = CityNotFoundException.class)
  public void handleExceptionsCityNotFound() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(
        new HttpClientErrorException(HttpStatus.NOT_FOUND), "Visnagar");
  }

  @Test(expected = InternalServerErrorException.class)
  public void handleExceptionsUnauthorized() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(
        new HttpClientErrorException(HttpStatus.UNAUTHORIZED), "Visnagar");
  }

  @Test(expected = Exception.class)
  public void handleExceptionsUnhandledHttpStatusCode() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(
        new HttpClientErrorException(HttpStatus.BAD_REQUEST), "Visnagar");
  }

  @Test(expected = InternalServerErrorException.class)
  public void handleExceptionsResourceAccessException() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(new ResourceAccessException(""), "Visnagar");
  }

  @Test(expected = InternalServerErrorException.class)
  public void handleExceptionsJsonSyntaxExceptionException() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(new JsonSyntaxException(""), "Visnagar");
  }

  @Test(expected = Exception.class)
  public void handleExceptionsGeneralExceptionException() throws Exception {
    openWeatherMapExceptionHandler.handleExceptions(new Exception(""), "Visnagar");
  }
}
