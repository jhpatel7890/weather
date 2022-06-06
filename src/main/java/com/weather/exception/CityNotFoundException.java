package com.weather.exception;

/** Custom exception to represent City Not found scenario */
public class CityNotFoundException extends Exception {
  public CityNotFoundException(String message) {
    super(message);
  }
}
