package com.weather.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.pojo.WeatherPrediction;
import com.weather.service.WeatherPredictionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weatherPrediction")
@Api(tags = "Weather Prediction Services", produces = "application/json")
public class WeatherPredictionController {
  private static final Logger logger = LogManager.getLogger(WeatherPredictionController.class);
  @Autowired WeatherPredictionService weatherPredictionService;

  /**
   * API to predict weather for the next 3 days using external weather data sources
   *
   * @param city - String
   * @return Json formatted weather prediction with different notifications
   */
  @RequestMapping(
      value = "/predictWeatherForNext3Days",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(
      value = "Predict weather for the next 3 days",
      notes = "Predict weather for the next 3 days")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Weather Predicted"),
        @ApiResponse(code = 404, message = "City not found"),
        @ApiResponse(code = 500, message = "Internal server error")
      })
  public ResponseEntity<String> predictWeatherForNext3Days(
      @RequestParam(value = "city", defaultValue = "london") String city) throws Exception {
    logger.info("-- predictWeatherForNext3Days city {}", city);
    WeatherPrediction weatherPredictionOutput = weatherPredictionService.getWeatherPrediction(city);
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    String output = gson.toJson(weatherPredictionOutput);
    logger.info("-- predictWeatherForNext3Days output {}", output);
    return new ResponseEntity<String>(output, getHttpStatusCode(weatherPredictionOutput));
  }

  private HttpStatus getHttpStatusCode(WeatherPrediction weatherPrediction) {
    if (weatherPrediction.getCode().equals("404")) {
      return HttpStatus.NOT_FOUND;
    } else if (weatherPrediction.getCode().equals("500")) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    } else {
      return HttpStatus.OK;
    }
  }
}
