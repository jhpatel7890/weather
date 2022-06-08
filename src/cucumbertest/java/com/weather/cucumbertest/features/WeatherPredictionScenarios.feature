@CucumberTest
Feature: Weather Prediction API scenarios

  @Scenario-0
  Scenario: Mock the input weather data configurations
  Given Mock the input weather data configurations
   | InputWeatherDataSource | openweathermap |
   | OpenWeatherMapAppId    | SampleAppId    |
   | InputWeatherDataUrl    | http://api.openweathermap.org/data/2.5/forecast |
   | InputWeatherDataUnits  | metric |
   | InputWeatherDataThresholdMaxTemp | 40 |
   | InputWeatherDataThresholdMaxWindSpeed | 5 |
   | InputWeatherDataCollectionDate | 2022-06-05 |

  @Scenario-1
  Scenario: Predict weather for London
  Given Mock the success weather data response of London city
  When Call the weather prediction api for city London
  Then Validate success response has below data 
    | index | date       | temp_min | temp_max | rainNotification |
    | 0     | 2022-06-05 | 12.36    | 13.82    | Carry umbrella   |
    | 1     | 2022-06-06 | 10.6     | 15.13    | Carry umbrella   |
    | 2     | 2022-06-07 | 10.27    | 20.34    | Carry umbrella   |

  @Scenario-2
  Scenario: Predict weather for Visnagar
  Given Mock the success weather data response of Visnagar city
  When Call the weather prediction api for city Visnagar
  Then Validate success response has below data
    | index | date       | temp_min | temp_max | stormNotification                  | tempNotification     | windNotification          |
    | 0     | 2022-06-05 | 29.12    | 39.88    | Dont step out! A Storm is brewing! |                      | Its too windy, watch out! |
    | 1     | 2022-06-06 | 26.12    | 40.92    |                                    | Use sunscreen lotion | Its too windy, watch out! |
    | 2     | 2022-06-07 | 26.36    | 39.9     |                                    |                      | Its too windy, watch out! |

  @Scenario-3
  Scenario: Predict weather for dummy city
  Given Mock the city not found weather data response of dummy city
  When Call failed weather prediction api for city dummy which raises city not found exception

  @Scenario-4
  Scenario: Internal server error exception got raised while predicting weather for Visnagar city
    Given Mock the internal server error exception weather data response of Visnagar city
    When Call failed weather prediction api for city Visnagar which raises internal server error exception
