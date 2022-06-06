package com.weather.processor.openweathermap.input;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input openweathermap weather data */
public class WeatherForcast {
  String cod;
  Integer message;
  Integer cnt;
  List<WeatherData> list;
  City city;

  @Override
  public String toString() {
    return "WeatherForcast [code="
        + cod
        + ", message="
        + message
        + ", "
        + "cnt="
        + cnt
        + ", list="
        + list
        + ", city="
        + city
        + "]";
  }
}
