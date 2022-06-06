package com.weather.processor.openweathermap.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent weather data */
public class Main {
  Double temp;
  Double feels_like;
  Double temp_min;
  Double temp_max;
  Long pressure;
  Long sea_level;
  Long grnd_level;
  Integer humidity;
  Double temp_kf;

  @Override
  public String toString() {
    return "Main [temp="
        + temp
        + ", feels_like="
        + feels_like
        + ", temp_min="
        + temp_min
        + ", temp_max="
        + temp_max
        + ", pressure="
        + pressure
        + ", sea_level="
        + sea_level
        + ", grnd_level="
        + grnd_level
        + ", humidity="
        + humidity
        + ", temp_kf="
        + temp_kf
        + "]";
  }
}
