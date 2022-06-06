# Base java docker image
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

MAINTAINER Jayshil

# Creating Work Dirtory /weather and coping the weather prediction jar file
RUN mkdir -p /weather
COPY build/libs/weather-0.0.1-SNAPSHOT.jar /weather/main.jar

#Createing non-root user and changing owenership of /weather directory
RUN addgroup -g 9000 -S appuser && adduser -u 9000 -SDH appuser -G appuser
RUN chown -R appuser:appuser /weather
RUN chown appuser:appuser /tmp
USER appuser
WORKDIR /weather

# Exposing 8080 port
EXPOSE 8080

# Setting java command to execute main jar as entrypoint
ENTRYPOINT ["java", "-jar", "/weather/main.jar"]

