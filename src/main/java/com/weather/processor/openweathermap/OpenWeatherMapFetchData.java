package com.weather.processor.openweathermap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.configuration.WeatherInputConfiguration;
import com.weather.processor.openweathermap.input.WeatherForcast;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class to fetch weather forcast data using openweathermap api */
@Component
public class OpenWeatherMapFetchData {
  @Autowired OpenWeatherMapCallApi openWeatherMapCallApi;
  @Autowired WeatherInputConfiguration weatherInputConfiguration;

  private static final Logger logger = LogManager.getLogger(OpenWeatherMapFetchData.class);

  /**
   * Builds the openweathermap url using input configurations and make get rest call and converts
   * the json data to WeatherForcast object
   *
   * @param city - String
   * @return weatherForcast - WeatherForcast
   * @throws Exception
   */
  public WeatherForcast fetchWeatherForcastData(String city) {
    String openWeatherMapUrl = buildOpenWeatherMapUrl(city);
    logger.info("-- fetchWeatherForcastData openWeatherMapUrl {}", openWeatherMapUrl);
    String result = openWeatherMapCallApi.getOpenWeatherMapRestData(openWeatherMapUrl);
    // Convert string weather data into WeatherForcast object using Gson
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    WeatherForcast weatherForcast = gson.fromJson(result, WeatherForcast.class);
    return weatherForcast;
  }

  /**
   * Method to build openweathermap url using input configurations like app id, units
   *
   * @param city - String
   * @return url - String
   */
  private String buildOpenWeatherMapUrl(String city) {
    StringBuilder urlBuilder =
        new StringBuilder(weatherInputConfiguration.getInputWeatherDataUrl())
            .append("?q=" + city)
            .append("&mode=json")
            .append("&units=" + weatherInputConfiguration.getInputWeatherDataUnits())
            .append("&appid=" + weatherInputConfiguration.getOpenWeatherAppId());
    String url = urlBuilder.toString();
    logger.info("-- buildOpenWeatherMapUrl url {}", url);
    return url;
  }
}
