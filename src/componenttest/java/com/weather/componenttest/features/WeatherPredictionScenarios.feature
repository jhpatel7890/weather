@ComponentTest
Feature: Weather Prediction success scenarios

  @Scenario-1
  Scenario: Predict weather for London
  Given Mock the success response from openweathermap of London city
  And Call the weather prediction api for city London
  Then Validate response has below data 
    | index | date       | temp_min | temp_max | rainNotification |
    | 0     | 2022-06-05 | 12.36    | 13.82    | Carry umbrella   |
    | 1     | 2022-06-06 | 10.6     | 15.13    | Carry umbrella   |
    | 2     | 2022-06-07 | 10.27    | 20.34    | Carry umbrella   |

  @Scenario-2
  Scenario: Predict weather for Visnagar
  Given Mock the success response from openweathermap of Visnagar city
  And Call the weather prediction api for city Visnagar
  Then Validate response has below data
    | index | date       | temp_min | temp_max | stormNotification                  | tempNotification     | windNotification          |
    | 0     | 2022-06-05 | 29.12    | 39.88    | Dont step out! A Storm is brewing! |                      | Its too windy, watch out! |
    | 1     | 2022-06-06 | 26.12    | 40.92    |                                    | Use sunscreen lotion | Its too windy, watch out! |
    | 2     | 2022-06-07 | 26.36    | 39.9     |                                    |                      | Its too windy, watch out! |

