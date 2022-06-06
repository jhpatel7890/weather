package com.weather.service;

import com.weather.exception.InternalServerErrorException;
import com.weather.processor.openweathermap.OpenWeatherMapPredictionProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPredictionProcessorFactoryTest {
  @InjectMocks WeatherPredictionProcessorFactory weatherPredictionProcessorFactory;
  @Mock ApplicationContext applicationContext;
  @Mock OpenWeatherMapPredictionProcessor openWeatherMapPredictionProcessor;

  @Test
  public void getWeatherPredictionProcessorTest() throws Exception {
    String dataSource = "openweathermap";
    Mockito.doReturn(openWeatherMapPredictionProcessor)
        .when(applicationContext)
        .getBean(dataSource);
    weatherPredictionProcessorFactory.getWeatherPredictionProcessor(dataSource);
  }

  @Test(expected = InternalServerErrorException.class)
  public void getWeatherPredictionProcessorInvalidWeatherDataSourceTest() throws Exception {
    String dataSource = "dummyweathersource";
    Mockito.doThrow(
            new NoSuchBeanDefinitionException("No bean named '" + dataSource + "' available"))
        .when(applicationContext)
        .getBean(dataSource);
    weatherPredictionProcessorFactory.getWeatherPredictionProcessor(dataSource);
  }
}
