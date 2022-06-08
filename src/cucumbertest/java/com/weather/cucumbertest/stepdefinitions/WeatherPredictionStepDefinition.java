package com.weather.cucumbertest.stepdefinitions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.pojo.WeatherPrediction;
import com.weather.pojo.WeatherPredictionPerDay;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class WeatherPredictionStepDefinition extends WeatherPredictionTestContext {
  private static final Logger logger = LogManager.getLogger(WeatherPredictionStepDefinition.class);
  private static Map<String, Object> dataStorage = new HashMap<>();

  @Given("^Mock the input weather data configurations$")
  public void mockInputWeatherDataConfigurations(DataTable dataTable) {
    Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
    if (dataMap.containsKey("InputWeatherDataSource")) {
      Mockito.doReturn(dataMap.get("InputWeatherDataSource"))
          .when(weatherInputConfiguration)
          .getInputWeatherDataSource();
      dataStorage.put("InputWeatherDataSource", dataMap.get("InputWeatherDataSource"));
    }
    if (dataMap.containsKey("OpenWeatherMapAppId")) {
      Mockito.doReturn(dataMap.get("OpenWeatherMapAppId"))
          .when(weatherInputConfiguration)
          .getOpenWeatherAppId();
      dataStorage.put("OpenWeatherMapAppId", dataMap.get("OpenWeatherMapAppId"));
    }
    if (dataMap.containsKey("InputWeatherDataUrl")) {
      Mockito.doReturn(dataMap.get("InputWeatherDataUrl"))
          .when(weatherInputConfiguration)
          .getInputWeatherDataUrl();
      dataStorage.put("InputWeatherDataUrl", dataMap.get("InputWeatherDataUrl"));
    }
    if (dataMap.containsKey("InputWeatherDataUnits")) {
      Mockito.doReturn(dataMap.get("InputWeatherDataUnits"))
          .when(weatherInputConfiguration)
          .getInputWeatherDataUnits();
      dataStorage.put("InputWeatherDataUnits", dataMap.get("InputWeatherDataUnits"));
    }
    if (dataMap.containsKey("InputWeatherDataThresholdMaxTemp"))
      Mockito.doReturn(Double.valueOf(dataMap.get("InputWeatherDataThresholdMaxTemp")))
          .when(weatherInputConfiguration)
          .getInputWeatherDataThresholdMaxTemp();
    if (dataMap.containsKey("InputWeatherDataThresholdMaxWindSpeed"))
      Mockito.doReturn(Integer.valueOf(dataMap.get("InputWeatherDataThresholdMaxWindSpeed")))
          .when(weatherInputConfiguration)
          .getInputWeatherDataThresholdMaxWindSpeed();
    if (dataMap.containsKey("InputWeatherDataCollectionDate")) {
      LocalDate inputWeatherDataCollectionDate =
          LocalDate.parse(dataMap.get("InputWeatherDataCollectionDate"));
      Mockito.doReturn(inputWeatherDataCollectionDate).when(dateUtil).getCurrentLocalDate();
    }
  }

  @Given("^Mock the success weather data response of (.*) city$")
  public void mockSuccessOpenWeatherMapResponse(String city) throws Exception {
    File resource = new ClassPathResource("testdata/openWeatherMap" + city + "Data.json").getFile();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    Mockito.doReturn(fetchedWeatherData)
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            (String) dataStorage.get("InputWeatherDataUrl")
                + "?q="
                + city
                + "&mode=json&units="
                + (String) dataStorage.get("InputWeatherDataUnits")
                + "&appid="
                + (String) dataStorage.get("OpenWeatherMapAppId"));
  }

  @Given("^Mock the city not found weather data response of (.*) city$")
  public void mockCityNotFoundFailureOpenWeatherMapResponse(String city) throws Exception {
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            (String) dataStorage.get("InputWeatherDataUrl")
                + "?q="
                + city
                + "&mode=json&units="
                + (String) dataStorage.get("InputWeatherDataUnits")
                + "&appid="
                + (String) dataStorage.get("OpenWeatherMapAppId"));
  }

  @Given("^Mock the internal server error exception weather data response of (.*) city$")
  public void mockInternalServerExceptionFailureOpenWeatherMapResponse(String city)
      throws Exception {
    Mockito.doThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED))
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            (String) dataStorage.get("InputWeatherDataUrl")
                + "?q="
                + city
                + "&mode=json&units="
                + (String) dataStorage.get("InputWeatherDataUnits")
                + "&appid="
                + (String) dataStorage.get("OpenWeatherMapAppId"));
  }

  @When("^Call the weather prediction api for city (.*)$")
  public void callWeatherPredictionApi(String city) {
    String result =
        restTemplate.getForObject(
            "http://localhost:8080/api/weatherPrediction/predictWeatherForNext3Days?city=" + city,
            String.class);
    dataStorage.put("weatherPredictionResult", result);
  }

  @Then("^Validate success response has below data$")
  public void validateSuccessResponse(List<Map<String, String>> weatherResponseList) {
    String result = (String) dataStorage.get("weatherPredictionResult");
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    WeatherPrediction weatherPrediction = gson.fromJson(result, WeatherPrediction.class);
    Assert.assertNotNull(weatherPrediction);
    Assert.assertEquals(3, weatherPrediction.getWeatherPredictionPerDay().size());
    List<WeatherPredictionPerDay> weatherPredictionPerDayList =
        weatherPrediction.getWeatherPredictionPerDay();
    for (Map<String, String> weatherPredictionMap : weatherResponseList) {
      Integer index = Integer.valueOf(weatherPredictionMap.get("index"));
      WeatherPredictionPerDay weatherPredictionPerDay = weatherPredictionPerDayList.get(index);
      if (weatherPredictionMap.containsKey("date")) {
        Assert.assertEquals(weatherPredictionMap.get("date"), weatherPredictionPerDay.getDate());
      }
      if (weatherPredictionMap.containsKey("temp_min")) {
        Assert.assertEquals(
            Double.valueOf(weatherPredictionMap.get("temp_min")),
            weatherPredictionPerDay.getTemp_min());
      }
      if (weatherPredictionMap.containsKey("temp_max")) {
        Assert.assertEquals(
            Double.valueOf(weatherPredictionMap.get("temp_max")),
            weatherPredictionPerDay.getTemp_max());
      }
      if (weatherPredictionMap.containsKey("rainNotification")) {
        Assert.assertEquals(
            weatherPredictionMap.get("rainNotification"),
            weatherPredictionPerDay.getRainNotification());
      }
      if (weatherPredictionMap.containsKey("tempNotification")) {
        Assert.assertEquals(
            weatherPredictionMap.get("tempNotification"),
            weatherPredictionPerDay.getTempNotification());
      }
      if (weatherPredictionMap.containsKey("stormNotification")) {
        Assert.assertEquals(
            weatherPredictionMap.get("stormNotification"),
            weatherPredictionPerDay.getStormNotification());
      }
      if (weatherPredictionMap.containsKey("windNotification")) {
        Assert.assertEquals(
            weatherPredictionMap.get("windNotification"),
            weatherPredictionPerDay.getWindNotification());
      }
    }
  }

  @When("^Call failed weather prediction api for city (.*) which raises city not found exception$")
  public void callWeatherPredictionApiWithCityNotFoundException(String city) {
    try {
      String result =
          restTemplate.getForObject(
              "http://localhost:8080/api/weatherPrediction/predictWeatherForNext3Days?city=" + city,
              String.class);
    } catch (HttpClientErrorException exception) {
      Assert.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
  }

  @When(
      "^Call failed weather prediction api for city (.*) which raises internal server error exception$")
  public void callWeatherPredictionApiWithInternalServerErrorException(String city) {
    try {
      String result =
          restTemplate.getForObject(
              "http://localhost:8080/api/weatherPrediction/predictWeatherForNext3Days?city=" + city,
              String.class);
    } catch (HttpServerErrorException exception) {
      Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }
  }
}
