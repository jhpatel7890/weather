package com.weather.configuration;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class ClockConfiguration {

  public LocalDate getCurrentLocalDate() {
    return LocalDate.now();
  }
}
