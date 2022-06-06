package com.weather.processor.openweathermap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** Class to call openweathermap api */
@Component
public class OpenWeatherMapCallApi {
  @Autowired RestTemplate restTemplate;

  private static final Logger logger = LogManager.getLogger(OpenWeatherMapCallApi.class);

  /**
   * Call the openweathermap rest endpoint and return the weather data in string format.
   *
   * @param url - String
   * @return result - String
   */
  public String getOpenWeatherMapRestData(String url) {
    String result = restTemplate.getForObject(url, String.class);
    return result;
  }
}
