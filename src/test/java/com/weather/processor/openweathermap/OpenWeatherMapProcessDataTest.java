package com.weather.processor.openweathermap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.configuration.WeatherInputConfiguration;
import com.weather.configuration.WeatherPredictionConfiguration;
import com.weather.pojo.WeatherPrediction;
import com.weather.pojo.WeatherPredictionPerDay;
import com.weather.processor.openweathermap.input.WeatherForcast;
import com.weather.util.DateUtil;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapProcessDataTest {
  @InjectMocks OpenWeatherMapProcessData openWeatherMapProcessData;
  @Mock WeatherInputConfiguration weatherInputConfiguration;
  @Mock WeatherPredictionConfiguration weatherPredictionConfiguration;
  @Mock OpenWeatherMapExceptionHandler openWeatherMapExceptionHandler;
  @Mock DateUtil dateUtil;
  private static final LocalDate LOCAL_DATE = LocalDate.of(2022, 06, 05);

  @Test
  public void predictWeatherLondonTest() throws Exception {
    String city = "London";
    File resource = new ClassPathResource("testdata/openWeatherMapLondonData.json").getFile();
    Mockito.doReturn((Double) 40.0)
        .when(weatherInputConfiguration)
        .getInputWeatherDataThresholdMaxTemp();
    Mockito.doReturn(LOCAL_DATE).when(dateUtil).getCurrentLocalDate();
    Mockito.doReturn(4).when(weatherInputConfiguration).getInputWeatherDataThresholdMaxWindSpeed();
    Mockito.doReturn(3).when(weatherPredictionConfiguration).getWeatherPredictionDays();
    Mockito.doReturn("Carry umbrella")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionRainNotifiction();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    WeatherForcast weatherForcast = gson.fromJson(fetchedWeatherData, WeatherForcast.class);
    WeatherPrediction result = openWeatherMapProcessData.processData(weatherForcast);
    Assert.assertNotNull(result);
    Assert.assertEquals("London", result.getCity());
    List<WeatherPredictionPerDay> weatherPredictionPerDayList = result.getWeatherPredictionPerDay();
    Assert.assertEquals(3, weatherPredictionPerDayList.size());
    WeatherPredictionPerDay weatherPredictionPerDay = weatherPredictionPerDayList.get(0);
    Assert.assertEquals("2022-06-05", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 12.36, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 13.82, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Carry umbrella", weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    Assert.assertNull(weatherPredictionPerDay.getWindNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(1);
    Assert.assertEquals("2022-06-06", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 10.6, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 15.13, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Carry umbrella", weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    Assert.assertNull(weatherPredictionPerDay.getWindNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(2);
    Assert.assertEquals("2022-06-07", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 10.27, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 20.34, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Carry umbrella", weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    Assert.assertNull(weatherPredictionPerDay.getWindNotification());
  }

  @Test
  public void predictWeatherVisnagarTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn(LOCAL_DATE).when(dateUtil).getCurrentLocalDate();
    File resource = new ClassPathResource("testdata/openWeatherMapVisnagarData.json").getFile();
    Mockito.doReturn((Double) 40.0)
        .when(weatherInputConfiguration)
        .getInputWeatherDataThresholdMaxTemp();
    Mockito.doReturn(5).when(weatherInputConfiguration).getInputWeatherDataThresholdMaxWindSpeed();
    Mockito.doReturn(3).when(weatherPredictionConfiguration).getWeatherPredictionDays();
    Mockito.doReturn("Use sunscreen lotion")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionTempNotifiction();
    Mockito.doReturn("Its too windy, watch out!")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionWindNotifiction();
    Mockito.doReturn("Dont step out! A Storm is brewing!")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionThunderstormNotifiction();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    WeatherForcast weatherForcast = gson.fromJson(fetchedWeatherData, WeatherForcast.class);
    WeatherPrediction result = openWeatherMapProcessData.processData(weatherForcast);
    Assert.assertNotNull(result);
    Assert.assertEquals("Visnagar", result.getCity());
    List<WeatherPredictionPerDay> weatherPredictionPerDayList = result.getWeatherPredictionPerDay();
    Assert.assertEquals(3, weatherPredictionPerDayList.size());
    WeatherPredictionPerDay weatherPredictionPerDay = weatherPredictionPerDayList.get(0);
    Assert.assertEquals("2022-06-05", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 29.12, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 39.88, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals(
        "Dont step out! A Storm is brewing!", weatherPredictionPerDay.getStormNotification());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(1);
    Assert.assertEquals("2022-06-06", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 26.12, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 40.92, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Use sunscreen lotion", weatherPredictionPerDay.getTempNotification());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(2);
    Assert.assertEquals("2022-06-07", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 26.36, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 39.9, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
  }

  @Test
  public void predictWeatherVisnagarFor4DaysTest() throws Exception {
    String city = "Visnagar";
    Mockito.doReturn(LOCAL_DATE).when(dateUtil).getCurrentLocalDate();
    File resource = new ClassPathResource("testdata/openWeatherMapVisnagarData.json").getFile();
    Mockito.doReturn((Double) 40.0)
        .when(weatherInputConfiguration)
        .getInputWeatherDataThresholdMaxTemp();
    Mockito.doReturn(5).when(weatherInputConfiguration).getInputWeatherDataThresholdMaxWindSpeed();
    Mockito.doReturn(4).when(weatherPredictionConfiguration).getWeatherPredictionDays();
    Mockito.doReturn("Use sunscreen lotion")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionTempNotifiction();
    Mockito.doReturn("Its too windy, watch out!")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionWindNotifiction();
    Mockito.doReturn("Dont step out! A Storm is brewing!")
        .when(weatherPredictionConfiguration)
        .getWeatherPredictionThunderstormNotifiction();
    String fetchedWeatherData = new String(Files.readAllBytes(resource.toPath()));
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    WeatherForcast weatherForcast = gson.fromJson(fetchedWeatherData, WeatherForcast.class);
    WeatherPrediction result = openWeatherMapProcessData.processData(weatherForcast);
    Assert.assertNotNull(result);
    Assert.assertEquals("Visnagar", result.getCity());
    List<WeatherPredictionPerDay> weatherPredictionPerDayList = result.getWeatherPredictionPerDay();
    Assert.assertEquals(4, weatherPredictionPerDayList.size());
    WeatherPredictionPerDay weatherPredictionPerDay = weatherPredictionPerDayList.get(0);
    Assert.assertEquals("2022-06-05", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 29.12, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 39.88, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals(
        "Dont step out! A Storm is brewing!", weatherPredictionPerDay.getStormNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(1);
    Assert.assertEquals("2022-06-06", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 26.12, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 40.92, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Use sunscreen lotion", weatherPredictionPerDay.getTempNotification());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(2);
    Assert.assertEquals("2022-06-07", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 26.36, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 39.9, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
    weatherPredictionPerDay = weatherPredictionPerDayList.get(3);
    Assert.assertEquals("2022-06-08", weatherPredictionPerDay.getDate());
    Assert.assertEquals((Double) 27.58, weatherPredictionPerDay.getTemp_min());
    Assert.assertEquals((Double) 39.31, weatherPredictionPerDay.getTemp_max());
    Assert.assertEquals("Its too windy, watch out!", weatherPredictionPerDay.getWindNotification());
    Assert.assertNull(weatherPredictionPerDay.getTempNotification());
    Assert.assertNull(weatherPredictionPerDay.getRainNotification());
    Assert.assertNull(weatherPredictionPerDay.getStormNotification());
  }
}
