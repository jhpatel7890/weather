package com.weather.processor.openweathermap.input;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent weather data for specific time */
public class WeatherData {
  Long dt;
  Main main;
  List<WeatherCondition> weather;
  Clouds clouds;
  Wind wind;
  Long visibility;
  Double pop;
  Rain rain;
  String dt_txt;

  @Override
  public String toString() {
    return "WeatherData [dt="
        + dt
        + ", main="
        + main
        + ", weather="
        + weather
        + ", clouds="
        + clouds
        + ", wind="
        + wind
        + ", visibility="
        + visibility
        + ", pop="
        + pop
        + ", rain="
        + rain
        + ", dt_txt="
        + dt_txt
        + "]";
  }
}
