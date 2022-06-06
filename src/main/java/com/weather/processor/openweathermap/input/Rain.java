package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input rain data of specific time */
public class Rain {
  Double h;

  @Override
  public String toString() {
    return "Rain [h=" + h + "]";
  }
}
