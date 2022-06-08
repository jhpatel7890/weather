# Weather Prediction
- This is a spring boot microservice which predicts weather based on different weather farcast data. 
- Currently we have added support for openweathermap api https://api.openweathermap.org/data/2.5/forecast weather forcast data to predict weather.
- Later if needed, we can also add support from any other weather forcast source as well with minimal code changes.
- This API predicts weather for next 3 days by default. The number of weather prediction days is configurable as application property
- It predicts below weather behaviours such as min/max temparature, provides notifications like rain, storm, wind, max temparatue. 

## Offline mode support
We have also added offline mode support where we are storing weather prediction data for supported citys. 
You can configure the list of supported citys as well frequency in seconds at which weather prediction needs to be stored for offline mode as application property. 

## API Details
URL : /api/weatherPrediction/predictWeatherForNext3Days?city=CITY&offlinemode=OFFLINEMODE
- Update CITY as the city name for which you want to predict weather
- Update OFFLINEMODE as y if you want offline mode support and by default value is n if not provided. 

## Useful command gradle & docker commands for this app
- To build : gradlew clean build 
- To run cucumber testcases : gradlew cucumbertest 
- To generate docker image : docker build -t IMAGE_NAME . 
- To Run as docker container : docker run -e OPEN_WEATHER_MAP_APP_ID=SampleId -p 8080:8080 -t IMAGE_NAME

## Class Diagram
![alt text](WeatherPredictionDiagram.drawio.png?raw=true)
