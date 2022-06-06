package com.weather.processor.openweathermap;

import com.weather.configuration.ClockConfiguration;
import com.weather.configuration.WeatherInputConfiguration;
import com.weather.configuration.WeatherPredictionConfiguration;
import com.weather.pojo.WeatherPrediction;
import com.weather.pojo.WeatherPredictionPerDay;
import com.weather.processor.openweathermap.input.Main;
import com.weather.processor.openweathermap.input.WeatherData;
import com.weather.processor.openweathermap.input.WeatherForcast;
import com.weather.processor.openweathermap.input.Wind;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class to process openweather forcast weather data */
@Component
public class OpenWeatherMapProcessData {
  @Autowired WeatherInputConfiguration weatherInputConfiguration;
  @Autowired WeatherPredictionConfiguration weatherPredictionConfiguration;
  @Autowired ClockConfiguration clockConfiguration;
  private static final Logger logger = LogManager.getLogger(OpenWeatherMapProcessData.class);

  /**
   * First it builds intermedieate tree map which has predicted weather for next days with various
   * notifications by going through input forcast records and then populates the output object of
   * WeatherPrediction
   *
   * @param weatherForcast - Input object of WeatherPrediction
   * @return weatherPrediction - Output weather prediction
   */
  public WeatherPrediction processData(WeatherForcast weatherForcast) {
    TreeMap<String, Map<String, Object>> weatherMap =
        buildIntermediateWeatherMapPerDay(weatherForcast);
    WeatherPrediction weatherPrediction =
        buildWeatherPrediction(weatherForcast.getCity().getName(), weatherMap);
    return weatherPrediction;
  }

  /**
   * Method to build intermediate tree map which contains processed weather data per day like
   * temp_min, temp_max and various notifications.
   *
   * @param weatherForcast - Input object of WeatherPrediction
   * @return weatherMap - Intermediate TreeMap which contains weather data per day
   */
  private TreeMap<String, Map<String, Object>> buildIntermediateWeatherMapPerDay(
      WeatherForcast weatherForcast) {
    TreeMap<String, Map<String, Object>> weatherMap = new TreeMap<String, Map<String, Object>>();
    for (WeatherData weatherData : weatherForcast.getList()) {
      // Extract date in yyyy-MM-dd format from dt_txt field
      String dateStr = fetchOnlyDateFromDtTxt(weatherData.getDt_txt());
      // Check whether date is within configured day limit for prediction
      if (isDateWithinPredictionDaysLimit(dateStr)) {
        Main main = weatherData.getMain();
        Map<String, Object> parameterMap = null;
        if (weatherMap.containsKey(dateStr)) {
          // Calculate the maximum temparature
          parameterMap = weatherMap.get(dateStr);
          Double currentTempMax = (Double) parameterMap.get("maxTemp");
          if (main.getTemp_max() > currentTempMax) {
            parameterMap.put("maxTemp", main.getTemp_max());
          }
          // Calculate the minimum temparature
          Double currentTempMin = (Double) parameterMap.get("minTemp");
          if (main.getTemp_min() < currentTempMin) {
            parameterMap.put("minTemp", main.getTemp_min());
          }
        } else {
          parameterMap = new HashMap<>();
          parameterMap.put("maxTemp", main.getTemp_max());
          parameterMap.put("minTemp", main.getTemp_min());
        }
        // Check whether rain notification needs to be set or not
        String weatherMain = weatherData.getWeather().get(0).getMain();
        if (weatherMain.equals("Rain")) {
          parameterMap.put(
              "rainNotification",
              weatherPredictionConfiguration.getWeatherPredictionRainNotifiction());
        }
        // Check whether storm notification needs to be set or not
        if (weatherMain.equals("Thunderstorm")) {
          parameterMap.put(
              "stormNotification",
              weatherPredictionConfiguration.getWeatherPredictionThunderstormNotifiction());
        }
        // Check whether wind notification needs to be set or not
        Wind wind = weatherData.getWind();
        if (wind.getSpeed()
            > weatherInputConfiguration.getInputWeatherDataThresholdMaxWindSpeed()) {
          parameterMap.put(
              "windNotification",
              weatherPredictionConfiguration.getWeatherPredictionWindNotifiction());
        }
        weatherMap.put(dateStr, parameterMap);
      }
    }
    return weatherMap;
  }

  /**
   * Extract date in yyyy-MM-dd format from dt_txt field which is in yyyy-MM-dd HH:mm:ss format
   *
   * @param dtTxt - String
   * @return dateStr - String
   */
  private String fetchOnlyDateFromDtTxt(String dtTxt) {
    String[] dtTxtArry = dtTxt.split(" ");
    String dateStr = dtTxtArry[0];
    return dateStr;
  }

  /**
   * Checks whether given date is within configured prediction days limit or not. This is to
   * determine whether we need to predict weather for that date or not
   *
   * @param dateStr - String
   * @return boolean
   */
  private boolean isDateWithinPredictionDaysLimit(String dateStr) {
    LocalDate currentDate = clockConfiguration.getCurrentLocalDate();
    LocalDate weatherDataDate = LocalDate.parse(dateStr);
    long days = ChronoUnit.DAYS.between(currentDate, weatherDataDate);
    if (days < weatherPredictionConfiguration.getWeatherPredictionDays()) {
      return true;
    }
    return false;
  }

  /**
   * Populates WeatherPrediction output object using intermediate processed weather tree map data
   * per day
   *
   * @param city - String
   * @param weatherMap - TreeMap
   * @return weatherPrediction - WeatherPrediction
   */
  private WeatherPrediction buildWeatherPrediction(
      String city, TreeMap<String, Map<String, Object>> weatherMap) {
    WeatherPrediction weatherPrediction = new WeatherPrediction();
    List<WeatherPredictionPerDay> weatherPredictionPerDays = new ArrayList<>();
    for (String dateTemp : weatherMap.keySet()) {
      WeatherPredictionPerDay weatherPredictionPerDay = new WeatherPredictionPerDay();
      weatherPredictionPerDay.setDate(dateTemp);
      Map<String, Object> parameterMap = weatherMap.get(dateTemp);
      weatherPredictionPerDay.setTemp_max((Double) parameterMap.get("maxTemp"));
      weatherPredictionPerDay.setTemp_min((Double) parameterMap.get("minTemp"));
      // Checks whether max temperature notification needs to be set or not
      if (weatherPredictionPerDay.getTemp_max()
          >= weatherInputConfiguration.getInputWeatherDataThresholdMaxTemp()) {
        weatherPredictionPerDay.setTempNotification(
            weatherPredictionConfiguration.getWeatherPredictionTempNotifiction());
      }
      weatherPredictionPerDay.setRainNotification((String) parameterMap.get("rainNotification"));
      weatherPredictionPerDay.setStormNotification((String) parameterMap.get("stormNotification"));
      weatherPredictionPerDay.setWindNotification((String) parameterMap.get("windNotification"));
      weatherPredictionPerDays.add(weatherPredictionPerDay);
    }
    weatherPrediction.setCity(city);
    weatherPrediction.setWeatherPredictionPerDay(weatherPredictionPerDays);
    return weatherPrediction;
  }
}
