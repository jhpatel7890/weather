package com.weather.processor.openweathermap;

import com.google.gson.JsonSyntaxException;
import com.weather.exception.CityNotFoundException;
import com.weather.exception.InternalServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

/** Class handling openweathermap related exceptions */
@Component
public class OpenWeatherMapExceptionHandler {

  private static final Logger logger = LogManager.getLogger(OpenWeatherMapExceptionHandler.class);

  /**
   * Handles various exceptions and raises CityNotFoundException or InternalServerException
   *
   * @param exception - Raised exception
   * @param city
   * @throws Exception
   */
  public void handleExceptions(Exception exception, String city) throws Exception {
    if (exception instanceof HttpClientErrorException) {
      HttpStatus httpStatus = ((HttpClientErrorException) exception).getStatusCode();
      if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
        throw new CityNotFoundException("City " + city + " not found");
      } else if (httpStatus.equals(HttpStatus.UNAUTHORIZED)) {
        throw new InternalServerErrorException("Invalid openweathermap Api Id");
      } else {
        throw exception;
      }
    } else if (exception instanceof ResourceAccessException) {
      throw new InternalServerErrorException("Openweathermap url is not reachable");
    } else if (exception instanceof JsonSyntaxException) {
      throw new InternalServerErrorException("Could not parse the openweather input data");
    } else {
      throw exception;
    }
  }
}
