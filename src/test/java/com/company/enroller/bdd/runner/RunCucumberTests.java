package com.company.enroller.bdd.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = { "com.company.enroller.bdd.steps" },
		dryRun = false)
public class RunCucumberTests {
}
