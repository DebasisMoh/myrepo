package utiilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.MediaEntityBuilder;

import com.sun.tools.sjavac.Log;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.java.Before;
import okio.Buffer;
import pageObjects.BaseClass;

public class CommonUtilities extends BaseClass {
	
	
	
public WebDriver driver ;
private String browser;
private int implicitWait;
private int pageLoadTimeout;
private String screenshotOnFail;
	

public CommonUtilities(WebDriver driver) {
	this.driver = driver;
	
}
	
	
	public String takescreenshot(String path,WebDriver driver) throws IOException {
	File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(srcFile, new File(path));
	return path;
}
	
	public void addCucumberStep(String description , String stepName , boolean addScreenshot , Status status) throws IOException {
		
		switch(status) {
		case FAIL:
			if(addScreenshot) {
				String path = takescreenshot(System.getProperty("user.dir")+"//Screenshots//"+stepName+".png", driver);
				//logger.fail(description,MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}else {
				//logger.log(com.aventstack.extentreports.extentreports.Status.FAIL, description);
				System.out.println("in else block");
		}
		break;
		case PASS:
			if(addScreenshot) {
				String path = takescreenshot(stepName, driver);
				Reporter.addStepLog(description);
				Reporter.addScreenCaptureFromPath(path);
			//	logger.pass(description,MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}else {
				Reporter.addStepLog(description);
				System.out.println("in else block");
				//logger.log(com.aventstack.extentreports.extentreports.Status.PASS, description);
				
		}
		break;
			
	}
//		System.out.println("Cucumber Step "+description+" added successfully");
//		
}

	
	
	public static String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}
	
	public  void Click(WebElement ele)
	{
	    ele.click();
	}
	
	public void OpenURL(String url ) throws IOException {//, String stepName , String message
		try {
			Properties cnfgProp = new Properties();
			FileInputStream ConfigProf = new FileInputStream(".//config.properties");
			cnfgProp.load(ConfigProf);
			String brwsr = cnfgProp.getProperty("browser");
			System.out.println(cnfgProp.getProperty("chromepath"));
			
			if(brwsr.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", cnfgProp.getProperty("chromepath"));
			    driver = new ChromeDriver();
			    System.out.println("browser is chrome");
			    driver.get(url);
			}else if(brwsr.equals("firefox")){
				System.setProperty("webdriver.gecko.driver", cnfgProp.getProperty("firefoxpath"));
			    driver = new FirefoxDriver();
			    driver.get(url);
			}else if(brwsr.equals("ie")) {
				System.setProperty("webdriver.ie.driver", cnfgProp.getProperty("iepath"));
				 driver = new InternetExplorerDriver();
				 driver.get(url);
			}else {
				System.setProperty("webdriver.chrome.driver", cnfgProp.getProperty("chromepath"));
			    driver = new ChromeDriver();
			    driver.get(url);
			}
			super.initializeObjects();
			
			addCucumberStep("Successful","stepName",true,Status.PASS);
		}catch (Exception e) {
			addCucumberStep("Successful","stepName",true,Status.FAIL);
			e.getMessage();
		}
	}
	
	
	public void openBrowser() throws IOException {
		System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		
		//return driver;
		

		
	}
	
	public void switchWindow(int window , String stepName)  throws IOException {
		try {
			Object[] windows = driver.getWindowHandles().toArray();
			driver.switchTo().window(windows[window].toString());
			addCucumberStep("SwitchWindow Successful",stepName,true,Status.PASS);
		}catch(Exception e) {
			addCucumberStep("SwitchWindow UnSuccessful",stepName,true,Status.FAIL);
			e.getMessage();
		}
		
	}
	
	public void closebrowser() {
		Object[] windows = driver.getWindowHandles().toArray();
		for(int i = 0 ; i<windows.length;i++) {
			driver.switchTo().window(windows[i].toString());
			driver.close();
		}
	}
	
	public void DriverFunctions(WebDriver driver, String browser, int implicitWait, int pageLoadTimeout) {
		this.driver=driver;
		this.browser=browser;
		this.implicitWait=implicitWait;
		this.pageLoadTimeout=pageLoadTimeout;
		
	}
	
	public static void closeAllBrowserWindows(WebDriver driver) {
		Set<String> handles = driver.getWindowHandles();
		if(handles.size()>1) {
			Log.info("Closing"+ handles.size() + "window(s).");
			for(String windowID : handles) {
				Log.info("--closing window handle: "+windowID);
				driver.switchTo().window(windowID).close();
			}
		}else if(handles.size() == 1) {
			Log.info("-- closing last opened window. --");
			
		}else {
			Log.info(" -- There were no window handles to close. --");
		}
		driver.quit();
		
	}
	
	public void unhighlight(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver ;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element,"");
		
	}
	
	public void highlightElement(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver ;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				element,"color: ; border: 5px solid yellow;");
		
	}
	

	
	public WebElement executeStep(WebElement element , String description) throws IOException {
		Log.info("-- Inside --" + description);
		String[] stepDetails = new String[3];
		int xCoordinate = 0;
		int yCoordinate = 0;
		String serielNo = randomString();
		String reportPathBrowser = null;
		String screenshotpath = reportPathBrowser + "\\screenshots\\" +serielNo+ ".png";
		String status;
		try {
			xCoordinate = element.getLocation().x;
			yCoordinate = element.getLocation().y;
			highlightElement(element);
			status = "pass";
			if(screenshotOnFail.equals("Y")) {
				System.out.println("Only screenshot will be captured in failed cases");
				
			}else {
				takescreenshot(screenshotpath,driver);
			}
			Thread.sleep(500);
			unhighlight(element);
		}catch(Exception e) {
			//updateStep("Element not Found", "Fail - Exception :" + "\n\t\t" + e.getClass().getSimpleName());
			Log.info("-- Element not Found --");
		} finally {
			stepDetails[0]= description ;
			//stepDetails[1]= status;
			stepDetails[2]= xCoordinate + "," + yCoordinate ;
			//serielNo++;
		}
	//	stepDetails.add(stepDetails);
		return element;
	
		
	}
	
	public void updateStep(String description ,String stepName, String status) {
		Log.info("************" + description );
		
		String[] stepDetails = new String[4]; 
		stepDetails[0] = description ;
		stepDetails[1] = status ;
		stepDetails[2] = 0 + ","+ 0 ;
		System.out.println("Update step***************************************"+stepDetails[0]);
		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		String ScreenPath = System.getProperty("user.dir") + "\\Screenshots\\" + stepName + ".png";
		if(screenshotOnFail.equalsIgnoreCase("Y")) {
			if(status.contains("Fail")) {
				try {
					takescreenshot(ScreenPath,driver);
					
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			try {
				takescreenshot(ScreenPath,driver);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * stepDetails.add(stepDetails); serielNo++ ; if(status.equalsIgnoreCase("Fail")
		 * && SuiteRunner.suiteRun == true) { AssertJunit.assertTrue(false); }
		 */
	}
	
	
	public void alertAccept() {
		Log.info("---Alert Accepted---");
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Log.info("---Inside Alert Accept---");
			updateStep("Alert Accept", "Steps","Pass");
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void alertReject() {
		Log.info("---Alert Rejected---");
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			Log.info("---Inside Alert Dismiss---");
			updateStep("Alert Dismiss", "Steps","Pass");
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	@SuppressWarnings("finally")
	public String getAlertData() {
		try {
			Log.info("----GET ALERT DATA----");
			Alert alert = driver.switchTo().alert();
			String text = alert.getText();
			return text;
		}catch(UnhandledAlertException e) {
			UnhandledAlertException uae = new UnhandledAlertException(browser);
			return uae.getAlertText();
		}
	}
	
	public void setAlert(String input) {
		Log.info("--- SET ALERT DATA---");
		try {
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(input);
			alert.accept();
			updateStep("Set Alert","Steps","Pass");
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	public Boolean isAlertPresent() {
		Log.info("--- IS ALERT PRESENT---");
		try {
			Alert alert = driver.switchTo().alert();
			updateStep("Alert Present","Steps","Pass");
			return true;
		}catch (NoAlertPresentException e) {
			updateStep("Alert Not Present","Steps","Pass");
			return false;
		}catch(UnhandledAlertException e2) {
			updateStep("Alert Present","Steps","Pass");
			return true;
		}
	}
	
	public Boolean isElementPresent(WebElement ele , String description) {
		Log.info("---IS ELEMENT PRESENT---");
		try {
			highlightElement(ele);
			updateStep("Element Found:"+ description ,"Steps", "Pass");
			unhighlight(ele);
			return true;
		}catch(Exception e) {
			e.getMessage();
			updateStep("Element Not Found:"+ description ,"Steps", "Fail");
			return false;
		}
	}
	
	public <T> T assertEquals(T Expected , T Actual) {
		try {
			Log.info("---CHECKS IF EQUAL---");
			Log.info("Exp :"+ Expected);
			Log.info("Act :"+ Actual);
			if(Expected.equals(Actual)) {
				updateStep("Validtion passed - Expected: "+Expected + "Actual :"+Actual ,"Steps","Pass");
			}else {
				updateStep("Validtion Failed - Expected: "+Expected + "Actual :"+Actual ,"Steps","Fail");
			}
		} catch(Exception e) {
			e.getMessage();
			updateStep("Validtion Failed - Expected: "+Expected + "Actual :"+Actual ,"Steps","Fail - Exception : "+"\n\t\t" + e.getClass().getSimpleName());
		}
		return Actual;
	}
	
	public void assertTrue(Boolean result) {
		Log.info("---CHECKS IF TRUE---");
		if(result.equals(true)) {
			updateStep("Validation Passed" ,"Steps", "Pass");
		}else {
			updateStep("Validation failed" ,"Steps","Fail");
		}
	}
	
	public String getStringValue(String baseword , String startword , String endword) {
		Log.info("---GET STRING VALUE---");
		int a = startword.length();
		int b = baseword.indexOf(startword);
		int startPoint = a + b;
		int length = baseword.substring(a+b).indexOf(endword);
		int endPoint = startPoint + length ;
		return(baseword.substring(startPoint, endPoint));
	}
	
	public void switchFrame(String frameId) {
		try {
			driver.switchTo().frame(frameId);
			updateStep("Switch to Frame "+ frameId ,"Steps", "Pass");
		}catch(Exception e) {
			e.getMessage();
			updateStep("Switch to Frame" + frameId ,"Steps" , "Fail - Exception: "+"\n\t\t" + e.getClass().getSimpleName());
		}
	}
	
	/*   This method tranfers the control to the frame present t the webelement  */
	
	public void switchFrame(WebElement element) {
		Log.info("--- SWITCH FRAME ---");
		try {
			driver.switchTo().frame(element);
			updateStep("Switch to Frame","Steps", "Pass");
			
		}catch(Exception e) {
			e.getMessage();
			updateStep("Switch to Frame" ,"Steps", "Fail - Exception: "+"\n\t\t" + e.getClass().getSimpleName());
		}
	}
	
	/* This method transfers the control from frames to the default frame
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	
	public void switchtoDefaultContent() throws IOException , InterruptedException {
		driver.switchTo().defaultContent();
		updateStep("Switch to Default Content","Steps", "Pass");
	}
	
	/* This method transfers the control from frames to the default frame
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	
	public void waitForElementPresent(WebElement element , int time_in_ms) throws InterruptedException {
		int waittime = 0;
		Boolean bin_found = false;
		while (waittime < time_in_ms) {
			try {
				highlightElement(element);
				updateStep("Element Found","Steps","Pass");
				bin_found = true;
				return;
				
			}catch(NoSuchElementException e) {
				waittime = waittime + 1000 ;
			}catch(InterruptedException e) {
				e.getMessage();
			}
		}
		if(bin_found == false) {
			updateStep("Element Not Found","Steps","Pass");
		}
	}
	
	public void wait(int time_in_ms) throws InterruptedException {
		Thread.sleep(time_in_ms);
	}
	
	public void executeJScript(String action , WebElement element) {
		try {
			highlightElement(element);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript(action, element);
			unhighlight(element);
			updateStep("Javascipt Command" ,"Steps", "Pass");
		}catch(Exception e) {
			updateStep("Error in Executing javaScript","Steps","Fail - Exception: "+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void executeJScriptClick( WebElement element) throws IOException , InterruptedException{
		try {
			highlightElement(element);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
			//unhighlight(element);
			updateStep("Element Clicked" ,"Steps", "Pass");
		}catch(Exception e) {
			updateStep("Error in Executing javaScript","Steps","Fail - Exception: "+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	@SuppressWarnings("finally")
	public String executeJScriptGetText( WebElement element) throws IOException , InterruptedException {
		String text = null;
		try {
			highlightElement(element);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			text = (String) js.executeScript("return arguments[0].value;", element);
			unhighlight(element);
			updateStep("JavaScript Command Executed" ,"Steps", "Pass");
		}catch(Exception e) {
			updateStep("Error in Executing javaScript","Steps","Fail - Exception: "+"\n\t\t"+e.getClass().getSimpleName());
			text = "";
		}finally {
			System.out.println("Text Returned is: "+ text);
			return text ;
		}
	}
	
	
	
	@SuppressWarnings("finally")
	public String getTitle( WebElement element) throws IOException , InterruptedException {
		String title = null;
		try {
			title = driver.getTitle();
			updateStep("Get The Title" ,"Steps", "Pass");
		}catch(Exception e) {
			updateStep("Get The Title","Steps","Fail - Exception: "+"\n\t\t"+e.getClass().getSimpleName());
			title = "";
		}finally {
			return title ;
		}
		
	}
	
	public Boolean assertTitle(String title) throws IOException , InterruptedException{
		try {
			if(driver.getTitle().contains(title)) {
				updateStep("Title matched"+title ,"Steps", "Pass");
				return true;
			}else {
				updateStep("Title mismatch- Expected :"+title + "Actual Title is :"+ driver.getTitle() ,"Steps" , "Fail");
				return false;
				
			}
		}catch(Exception e) {
			e.getMessage();
			updateStep("Error in getting page title","Steps", "Fail - Exception: "+"\n\t\t"+e.getClass().getSimpleName());
			return false;
		}
		
		
	}
	
	public void back() throws IOException , InterruptedException {
		try {
			driver.navigate().back();
			updateStep("navigate To Previous Page","Steps", "Pass");
		}catch(Exception e) {
			updateStep("Navigate To Previous Page","Steps", "Fail");
		}
	}
	
	public void forward() throws IOException , InterruptedException {
		try {
			driver.navigate().forward();
			updateStep("navigate To Next Page","Steps", "Pass");
		}catch(Exception e) {
			updateStep("navigate To Next Page","Steps", "Fail");
		}
	}
	
	public void refresh() throws IOException , InterruptedException {
		try {
			driver.navigate().refresh();
			updateStep("Refresh Page","Steps", "Pass");
		}catch(Exception e) {
			updateStep("Refresh Page","Steps", "Fail");
		}
	}
	
	public void click(WebElement ele , String desription) throws IOException , InterruptedException  {
		try {
			highlightElement(ele);
			updateStep(desription,"Steps", "Pass");
			unhighlight(ele);
			ele.click();
		}catch(NoSuchElementException e) {
			updateStep(desription ,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			Log.error(desription +"Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			
		} catch(Exception e) {
			updateStep(desription,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			Log.error(desription + "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void type(WebElement ele , String Value , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			ele.clear();
			ele.sendKeys(Value);
			updateStep(description,"Steps", "Pass");
			Log.info(description + ": pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName()); 
		    Log.error(description + "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void selectDropdownByVisibleText(WebElement ele , String Value , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Select se = new Select(ele);
			se.selectByVisibleText(Value);
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void selectDropdownByVisibleValue(WebElement ele , String Value , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Select se = new Select(ele);
			se.selectByValue(Value);
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void selectDropdownByIndex(WebElement ele , int index , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Select se = new Select(ele);
			se.selectByIndex(index);
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	
	public Boolean assertTextEquals(WebElement ele , String expText) throws IOException , InterruptedException {
		String actText;
		try {
			highlightElement(ele);
			actText = ele.getText();
			if(actText.equalsIgnoreCase(expText)) {
				updateStep("Text matched :" + expText,"Steps", "Pass");
				unhighlight(ele);
				return true;
			}else {
				updateStep("Text not Matched"+ expText + "Actual : "+actText,"Steps", "Fail");
				return false ;
			}
		}catch(Exception e) {
			updateStep("Error while retrieving Text","Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			return false;
		}
		
	}
	
	public Boolean assertTextContains (WebElement ele , String expText) throws IOException , InterruptedException {
		String actText ;
		try {
			highlightElement(ele);
			actText = ele.getText();
			if(actText.contains(expText)) {
				updateStep("Text Present :"+ expText,"Steps", "Pass");
				return true;
			}else {
				updateStep("Text not Present : Expected - "+ expText+"Actual :"+actText ,"Steps", "Fail");
				return false ;
			}
			
		}catch(Exception e) {
			updateStep("Error while Retrieving Text","Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			return false ;
		}
	}
	
	public void mouseOver (WebElement ele , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Actions act = new Actions(driver);
			act.moveToElement(ele).build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void clickAt(WebElement ele , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Actions act = new Actions(driver);
			act.click(ele).build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			
		}
	}
	
	public void doubleClick(WebElement ele , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Actions act = new Actions(driver);
			act.doubleClick(ele).build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			
		}
	}
	
	public void rightClick(WebElement ele , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Actions act = new Actions(driver);
			act.moveToElement(ele).contextClick().build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			
		}
	}
	
	public void dragAndDrop(WebElement ele1 , WebElement ele2 , String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele1);
			highlightElement(ele2);
			Actions act = new Actions(driver);
			act.dragAndDrop(ele1, ele2).build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele1);
			unhighlight(ele2);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public void slideBy(WebElement ele , int x,int y, String description) throws IOException , InterruptedException {
		try {
			highlightElement(ele);
			Actions act = new Actions(driver);
			act.dragAndDropBy(ele, x, y).build().perform();
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
		}
	}
	
	public String getCurrentURL() {
		logger.info("--- Get The Current URL---");
		return driver.getCurrentUrl();
	}
	
	public String getAttribute(WebElement ele , String attribute , String description) throws IOException , InterruptedException {
		Log.info("--- Get The Attribute Value ---");
		try {
			highlightElement(ele);
			updateStep(description,"Steps", "Pass");
			unhighlight(ele);
			return ele.getAttribute(attribute);
		}catch(Exception e) {
			updateStep(description,"Steps", "Fail - Exception :"+"\n\t\t"+e.getClass().getSimpleName());
			return null;
		}
	}
	
	public void fileUpload(WebElement ele , String path) throws IOException {
		File file = new File(path);
		path = file.getCanonicalPath();
		String cmd = null;
		if(driver instanceof FirefoxDriver) {
			cmd = ".\\Drivers\\geckodriver.exe";
		}
		if(driver instanceof ChromeDriver) {
			cmd = ".\\Drivers\\chromedriver.exe";
		}
		if(driver instanceof InternetExplorerDriver) {
			cmd = ".\\Drivers\\IEDriverServer.exe";
		}
		
		try {
			highlightElement(ele);
			updateStep("Click on Browse button for file upload","Steps", "Pass");
			ele.click();
			unhighlight(ele);
			ProcessBuilder pb = new ProcessBuilder(cmd,path);
			pb.start();
		}catch(Exception e) {
			updateStep("Exception while clicking Browse Button","Steps", "Fail - Exception :\"+\"\\n\\t\\t\"+e.getClass().getSimpleName()");
		}
	}
	
	public void fileUploadUsingRobot(String fileName) throws AWTException , InterruptedException {
		
		StringSelection ss = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
	}
	
	public void pressEscape() {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).build().perform();
	}
	
	public void pressEnter() {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).build().perform();
	}
	
	/*
	 * public void endReport() { System.out.
	 * println("################# End Of Execution #######################");
	 * generateMethodInfo(); super endOfTestCase = true; }
	 */
	
	/*
	 * public void generateTestStep() { Log.info("After Method of "+ Browser);
	 * reportPathBrowser = reportPath ;
	 * 
	 * @SupressWarnings("unchecked") ArrayList<String[]> newList =
	 * (ArrayList<String[]>) stepsDetails.clone();
	 * System.out.println("stepsDetails reportPathBrowser : ----------------> "+
	 * reportPathBrowser); testDetails.add(newList);
	 * System.out.println("Array test case details --------->"+ testDetails);
	 * serielNo = 1;
	 * 
	 * }
	 */
	
	public String readFromBuffer(String key) {
		String value = "";
		try {
		//	value = Buffer.getBuffer(key);
			
		}catch(Exception e) {
			updateStep("Exception while reading the key from buffer"+ key,"Steps", "Fail");
		}
		return value;
	}
	
	/*
	 * public String writeIntoBuffer(String key ,String value) { try { value =
	 * Buffer.setBuffer(key,value);
	 * 
	 * }catch(IOException e) {
	 * updateStep("Exception while writting the key value pair from buffer"+ key +
	 * "," + value ,"Steps", "Fail"); } }
	 */
	
	public int generateRandomNumber(int range , int startNum) {
		Random rn = new Random();
		int val = rn.nextInt() + startNum ;
		return val;
	}
	
	
	public void typeusingRobot(String fileName) throws AWTException , InterruptedException {
		StringSelection ss = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
				
	}
	
	
	public void pressTabUsingRobot() throws  AWTException , InterruptedException  {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		
	}
	
	public void pressTAB() {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
	}
	
	public void pressEnterUsingRobot() throws AWTException , InterruptedException{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	// 9437142643 madan jha
	
	public String getTheNewestFILE(String filepath , String ext ) {
		File theNewestFile = null;
		File dir = new File(filepath);
		FileFilter filefilter = new WildcardFileFilter("*."+ext);
		File[] files = dir.listFiles(filefilter);
		if(files.length>0) {
			Arrays.sort(files,LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}
		System.out.println(theNewestFile.getName());
		return theNewestFile.getName();
				
	}
	
	/*
	 * public void generateMethodInfo() { //Log.info("After Method Of "+ Browser);
	 * reportPathBrowser = reportPath ;
	 * 
	 * @SuppressWarnings("Unchecked") ArrayList<String[]> newList =
	 * (ArrayList<String[]>) stepDetails.clone(); testDetails.add(newList); serielNo
	 * = 1; }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



//	private int getRandomIntegerBetweenRange(int i, int j) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	
	
	

	

}
