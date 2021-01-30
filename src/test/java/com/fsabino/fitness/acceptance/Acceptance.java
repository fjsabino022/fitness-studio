package com.fsabino.fitness.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/acceptance/", plugin = {
		"pretty", 
		"html:target/acceptance", 
		"json:target/acceptance/cucumber.json", 
		"junit:target/acceptance/cucumber.xml"
	})
public class Acceptance {

}
