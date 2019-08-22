package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import utiilities.CommonUtilities;

public class BaseClass {
	
	public static Properties cnfgProp;
	//public static Properties OR = new Properties();
	public static Logger logger = Logger.getLogger("essplAutomation");
	public static WebDriverWait wait;
	public static FileInputStream fis;
	//public static Properties config = new Properties();
	public static WebDriver driver;
	public static LoginPage lgnPg;
	public static CustomerOperation cusOpt;
	public static SearchCustomer sc;
	public static CommonUtilities cm;
	

	public void initializeObjects() {
		lgnPg=new LoginPage(driver);
		cusOpt = new CustomerOperation(driver);
		sc = new SearchCustomer(driver);
		cm = new CommonUtilities(driver);
	}
	
	
	
}
