package com.weather.cucumbertest.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/cucumbertest/java/com/weather/cucumbertest/features"},
    glue = "com.weather.cucumbertest.stepdefinitions",
    tags = "@CucumberTest",
    plugin = {"json:target/cucumber.json", "html:target/cucumber-reports"})
public class RunCucumberTest {}
