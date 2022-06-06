package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input clouds information for specific time */
public class Clouds {
  Integer all;

  @Override
  public String toString() {
    return "Clouds [all=" + all + "]";
  }
}
