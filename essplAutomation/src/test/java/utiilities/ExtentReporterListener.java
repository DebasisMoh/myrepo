package utiilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pageObjects.BaseClass;

public class ExtentReporterListener extends BaseClass {
	
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	public static void reportSetUP() {
		
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(cnfgProp.getProperty("reportPath")+"//index.html");
		reporter.config().setDocumentTitle("Automation Test Report");
		reporter.config().setReportName("Automation Test Report");
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setChartVisibilityOnOpen(true);
		
		extent = new ExtentReports();
		extent.setSystemInfo("Environment",cnfgProp.getProperty("environment"));
		extent.setSystemInfo("OS Name",System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Application",cnfgProp.getProperty("application"));
		extent.attachReporter(reporter);
	}
	
	public static void addCucumberTest(String testNames) {
		logger = extent.createTest(testNames);
	}
	
	public void FlushCucumberTest() {
		extent.flush();
	}
	
	


}



