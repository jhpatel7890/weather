package com.weather.componenttest.stepdefinitions;

import com.weather.WeatherPredictionApplication;
import com.weather.configuration.WeatherInputConfiguration;
import com.weather.processor.openweathermap.OpenWeatherMapCallApi;
import com.weather.util.DateUtil;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@ContextConfiguration
@SpringBootTest(
    classes = {WeatherPredictionApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan({"com.weather"})
public class WeatherPredictionTestContext {
  @Autowired RestTemplate restTemplate;
  @Autowired @MockBean DateUtil dateUtil;
  @Autowired @MockBean OpenWeatherMapCallApi openWeatherMapCallApi;
  @Autowired @MockBean WeatherInputConfiguration weatherInputConfiguration;
}
