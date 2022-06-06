package com.weather.componenttest.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/componenttest/java/com/weather/componenttest/features"},
    glue = "com.weather.componenttest.stepdefinitions",
    tags = "@ComponentTest",
    plugin = {"json:target/cucumber.json", "html:target/cucumber-reports"})
public class RunComponentTest {}
