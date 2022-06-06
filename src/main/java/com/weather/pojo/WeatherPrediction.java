package com.weather.pojo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent weather prediction output */
public class WeatherPrediction {
  // Api http status code
  String code;
  // Api success or error message
  String message;
  // City for which weather prediction is requested
  String city;
  // List which contains weather prediction per day
  List<WeatherPredictionPerDay> weatherPredictionPerDay;

  @Override
  public String toString() {
    return "WeatherPrediction [weatherPredictionPerDay=" + weatherPredictionPerDay + "]";
  }
}
