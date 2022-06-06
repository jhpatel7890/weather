package com.weather.componenttest.stepdefinitions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.pojo.WeatherPrediction;
import com.weather.pojo.WeatherPredictionPerDay;
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

public class WeatherPredictionStepDefinition extends WeatherPredictionTestContext {
  private static final Logger logger = LogManager.getLogger(WeatherPredictionStepDefinition.class);
  private static Map<String, Object> dataStorage = new HashMap<>();
  private static final LocalDate LOCAL_DATE = LocalDate.of(2022, 06, 05);

  @Given("^Mock the success response from openweathermap of (.*) city$")
  public void mockSuccessOpenWeatherMapResponse(String city) throws Exception {
    Mockito.doReturn(LOCAL_DATE).when(clockConfiguration).getCurrentLocalDate();

    Mockito.doReturn("openweathermap").when(weatherInputConfiguration).getInputWeatherDataSource();
    Mockito.doReturn("http://api.openweathermap.org/data/2.5/forecast")
        .when(weatherInputConfiguration)
        .getInputWeatherDataUrl();
    Mockito.doReturn("metric").when(weatherInputConfiguration).getInputWeatherDataUnits();
    Mockito.doReturn("SampleAppId").when(weatherInputConfiguration).getOpenWeatherAppId();
    Mockito.doReturn((Double) 40.0)
        .when(weatherInputConfiguration)
        .getInputWeatherDataThresholdMaxTemp();
    Mockito.doReturn(4).when(weatherInputConfiguration).getInputWeatherDataThresholdMaxWindSpeed();
    File resource = new ClassPathResource("testdata/openWeatherMap" + city + "Data.json").getFile();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    Mockito.doReturn(fetchedWeatherData)
        .when(openWeatherMapCallApi)
        .getOpenWeatherMapRestData(
            "http://api.openweathermap.org/data/2.5/forecast?q="
                + city
                + "&mode=json&units=metric&appid=SampleAppId");
  }

  @When("^Call the weather prediction api for city (.*)$")
  public void callWeatherPredictionApi(String city) {
    String result =
        restTemplate.getForObject(
            "http://localhost:8080/api/weatherPrediction/predictWeatherForNext3Days?city=" + city,
            String.class);
    dataStorage.put("weatherPredictionResult", result);
  }

  @Then("^Validate response has below data$")
  public void getUserScanMode(List<Map<String, String>> weatherResponseList) {
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
}
