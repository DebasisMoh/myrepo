package testRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.CucumberOptions;
import pageObjects.BaseClass;
//import utiilities.ExtentReporterListener;
import utiilities.ExtentReporterListener;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions
	(
		features=".//Features/Login.feature",///CustomerOperation.feature",//Login//if you want to run all feature file available on the feature folder then ".//Features" 
		glue= {"stepDefinations"},//,//It will inform on which packages steps are implimented
		//dryRun=false,
		plugin= {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
	//	monochrome = true
	//	tags= {"@sanity"}// {"@sanity , @regression"}
		
	)//hi
public class TestRunner {  // extends ExtendedTestNGRunner 
	
	public void reportSetUP() throws FileNotFoundException{
		ExtentReporterListener.reportSetUP();
	}
}
