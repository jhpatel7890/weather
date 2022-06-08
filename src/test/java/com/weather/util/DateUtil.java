package com.weather.util;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

/** Util class related to Date */
@Component
public class DateUtil {

  /**
   * Returns the object of LocData with current date
   *
   * @return Object of LocalDate
   */
  public LocalDate getCurrentLocalDate() {
    return LocalDate.now();
  }
}
