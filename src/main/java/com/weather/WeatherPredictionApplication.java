package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.weather")
@SpringBootApplication
@EnableSwagger2
public class WeatherPredictionApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeatherPredictionApplication.class, args);
  }

  /**
   * Method to add support for swagger2 api documentation
   *
   * @return Docker object
   */
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.weather"))
        .build();
  }
}
