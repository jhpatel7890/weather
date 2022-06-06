package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent weather condition for specific time */
public class WeatherCondition {
  Integer id;
  String main;
  String description;
  String icon;

  @Override
  public String toString() {
    return "WeatherCondition [id="
        + id
        + ", main="
        + main
        + ", "
        + "description="
        + description
        + ", icon="
        + icon
        + "]";
  }
}
