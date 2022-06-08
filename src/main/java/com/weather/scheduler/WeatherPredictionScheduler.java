package com.weather.scheduler;

import com.weather.configuration.WeatherInputConfiguration;
import com.weather.pojo.WeatherPrediction;
import com.weather.service.WeatherPredictionService;
import com.weather.util.WeatherPredictionCacheUtil;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Scheduler class to store WeatherPrediction per city internal hashmap */
@Component
public class WeatherPredictionScheduler {
  @Autowired WeatherInputConfiguration weatherInputConfiguration;
  @Autowired WeatherPredictionService weatherPredictionService;
  @Autowired WeatherPredictionCacheUtil weatherPredictionCacheUtil;

  private static final Logger logger = LogManager.getLogger(WeatherPredictionScheduler.class);

  @Scheduled(
      fixedRateString = "${weather.data.offline.frequency.value}",
      timeUnit = TimeUnit.SECONDS)
  public void collectWeatherPredictionDataForSupportedCitys() {
    String weatherDataOfflineSupportedCitys =
        weatherInputConfiguration.getWeatherDataOfflineSupportedCitys();
    logger.info(
        "-- collectWeatherPredictionDataForSupportedCitys weatherDataOfflineSupportedCitys {}",
        weatherDataOfflineSupportedCitys);
    String[] weatherDataOfflineSupportedCityList = weatherDataOfflineSupportedCitys.split(",");
    for (int index = 0; index < weatherDataOfflineSupportedCityList.length; index++) {
      String city = weatherDataOfflineSupportedCityList[index].toLowerCase();
      WeatherPrediction weatherPrediction =
          weatherPredictionService.getWeatherPrediction(city, "n");
      if (weatherPrediction.getCode().equals("200"))
        weatherPredictionCacheUtil.updateWeatherPredictionCacheData(city, weatherPrediction);
      else
        logger.error(
            "-- collectWeatherPredictionDataForSupportedCitys Couldnt collect weather prediction for city {}",
            city);
    }
  }
}
