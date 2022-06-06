package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input co-ordinates of given city */
public class Coord {
  Double lat;
  Double lon;

  @Override
  public String toString() {
    return "Coord [lat=" + lat + ", lon=" + lon + "]";
  }
}
