package com.weather.util;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

  public LocalDate getCurrentLocalDate() {
    return LocalDate.now();
  }
}
