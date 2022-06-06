package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent input city information */
public class City {
  Long id;
  String name;
  String country;
  Integer population;
  Integer timezone;
  Long sunrise;
  Long sunset;
  Coord coord;

  @Override
  public String toString() {
    return "City [id="
        + id
        + ", name="
        + name
        + ", coord="
        + coord
        + ", country="
        + country
        + ", population="
        + population
        + ", timezone="
        + timezone
        + ", sunrise="
        + sunrise
        + ", sunset="
        + sunset
        + "]";
  }
}
