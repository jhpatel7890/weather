package com.weather.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** PoJo class to represent weather prediction per day */
public class WeatherPredictionPerDay {
  // Date in yyyy-MM-dd format
  String date;
  // Minimum temperature of the day
  Double temp_min;
  // Maximum temperature of the day
  Double temp_max;
  // Notification to indicate when temp_max goes beyond configured threshold
  String tempNotification;
  // Notification to indicate when rain is predicted for the day
  String rainNotification;
  // Notification to indicate when wind speed goes beyond configured threshold
  String windNotification;
  // Notification to indicate when storm is predicted for the day
  String stormNotification;

  @Override
  public String toString() {
    return "WeatherPredictionPerDay [date="
        + date
        + ", temp_min="
        + temp_min
        + ", temp_max="
        + temp_max
        + ", tempNotification="
        + tempNotification
        + ", rainNotification="
        + rainNotification
        + ", windNotification="
        + windNotification
        + ", stormNotification="
        + stormNotification
        + "]";
  }
}
