package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input wind data of specific time */
public class Wind {
  Double speed;
  Integer deg;
  Double gust;

  @Override
  public String toString() {
    return "Wind [speed=" + speed + ", deg=" + deg + ", " + "gust=" + gust + "]";
  }
}
