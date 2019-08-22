package stepDefinations;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import pageObjects.BaseClass;


public class Steps extends BaseClass {
	 
	
	@Before()
	public void setUp() throws IOException {
	PropertyConfigurator.configure("Log4j.properties");
		
		System.out.println("-------------------------error in setup----------------------------------");
		
		cnfgProp = new Properties();
		FileInputStream ConfigProf = new FileInputStream("config.properties");
		cnfgProp.load(ConfigProf);
		String brwsr = cnfgProp.getProperty("browser");
		String URL = cnfgProp.getProperty("URL");
		System.out.println(cnfgProp.getProperty("chromepath"));
		
		if(brwsr.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", cnfgProp.getProperty("chromepath"));
		    driver = new ChromeDriver();
		    System.out.println("browser is chrome");
		    driver.get(URL);
		}else if(brwsr.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", cnfgProp.getProperty("firefoxpath"));
		    driver = new FirefoxDriver();
		}else if(brwsr.equals("ie")) {
			System.setProperty("webdriver.ie.driver", cnfgProp.getProperty("iepath"));
			 driver = new InternetExplorerDriver();
			
		}else {
			System.setProperty("webdriver.chrome.driver", cnfgProp.getProperty("chromepath"));
		    driver = new ChromeDriver();
		}
		//super.initializeObjects();
		
	}
	

	
	
//	@When("^User open URL \"([^\"]*)\"$")
//	public void user_open_URL(String url) throws Exception {
//		super.initializeObjects();
//		logger.info("------Opening URL------");
//		System.out.println(driver);
//		cm.OpenURL(url);
//	   // driver.get(URL);
//	    Thread.sleep(5000);
//	    
//	}
	
	@And("^User Enter email as \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void user_Enter_email_as_and_password_as(String email, String password) {
		super.initializeObjects();
		System.out.println("credential fields-------------------------------------------------");
		logger.info("------Credential provided------");
	   lgnPg.setUserName(email);
	   lgnPg.setPassword(password);
	   
	}

	@And("^Click on Login$")
	public void click_on_Login()  {
		logger.info("------Login------");
	   lgnPg.clkSubmit();
	}

	@Then("^Page title should be \"([^\"]*)\"$")
	public void page_title_should_be(String title){
	   if(driver.getPageSource().contains("Your request failed to complete")) {
		   System.out.println("In if block");
		   driver.close();
		   Assert.assertTrue(false);
	   }
	   else {
		   System.out.println("In else block");
		   Assert.assertEquals(title, driver.getTitle());
	   }
	}
	
	@Then("^Close The Browser$")
	public void close_The_Browser() throws Exception{
		logger.info("------Browser closed------");
	   driver.close();
	}
	
//	// Customer Addition page stepdefinations..
//	
//	@Then("^user should land on the Dashboard page$")
//	public void user_should_land_on_the_Dashboard_page(){
////		cusOpt = new CustomerOperation(driver);
//		logger.info("------Customer dashboard page------");
//		Assert.assertEquals("Dashboard / nopCommerce administration", cusOpt.getPageTitle());
//		
//	}
//	
//	@Then("^user should click on customer which is on the left sidebar$")
//	public void user_should_click_on_customer_which_is_on_the_left_sidebar() throws Exception  {
//		Thread.sleep(2000);
//		cusOpt.clickOnCustomersMenu();
//		Thread.sleep(2000);
//	}
//
//	@Then("^Click on Customer submenu of Customer menubar$")
//	public void click_on_Customer_submenu_of_Customer_menubar() throws Exception {
//		cusOpt.clickonCustomerMenuItem();
//		Thread.sleep(2000);
//	}
//
//	@Then("^Click on Add New button$")
//	public void click_on_Add_New_button() throws Exception {
//		logger.info("------Customer Add New page------");
//		cusOpt.clickOnAddNew();
//		 Thread.sleep(2000);
//	}
//
//	
//
//	@Then("^User should fill all the details and save it$")
//	public void user_should_fill_all_the_details_and_save_it() throws Exception{
//		logger.info("------Customer Details page------");
//	    String email = cm.randomString()+"@gmail.com";
//	    cusOpt.setEmail(email);
//	    cusOpt.setPasswrd("12345@ABC");
//	    cusOpt.setFname("Debasis");
//	    cusOpt.setLname("Mohanty");
//	    cusOpt.setGender("Male");
//	    cusOpt.setDOB("04-07-1989");
//	    cusOpt.save();
//	    Thread.sleep(3000);
//	    
//	    
//	}
//	
//	@Then("^User can see the confirmation message as \"([^\"]*)\"$")
//	public void user_can_see_the_confirmation_message_as(String arg1) throws Exception {
//		logger.info("------Confirmation page------");
//	    Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("The new customer has been added successfully"));
//	}
//
//	@Then("^click on logout$")
//	public void click_on_logout() throws Exception {
//		logger.info("------Logout page------");
//		lgnPg.logout();
//	}
//	
//	//Steps for searching a customer by using Email
//	
//	@Given("^Enter Customer EmailID$")
//	public void enter_Customer_EmailID(){
//		//sc = new SearchCustomer(driver);
//		logger.info("------Provide email for search------");
//		sc.setEmailSrch("admin@yourStore.com");
//	}
//
//	@When("^Click on Search button$")
//	public void click_on_Search_button() throws Exception{
//		Thread.sleep(2000);
//		logger.info("------Search Customer------");
//		sc.clkSrchmailbtn();
//		Thread.sleep(3000);
//	}
//
//	@Then("^User should found Email on the search table$")
//	public void user_should_found_Email_on_the_search_table(){
//		boolean ststus = sc.searchCustomerByEmail("admin@yourStore.com");
//		Assert.assertEquals(true, ststus);
//	}
//	
//	//Search Customer using First Name
//
//	
//	@Then("^Enter Customer FirstName during search$")
//	public void enter_Customer_FirstName_during_search() throws Exception {
//		logger.info("------Search customer By Fname------");
//	    sc.setFnameSrch("abc");
//	}
//
//	@Then("^Enter Customer LastName during search$")
//	public void enter_Customer_LastName_during_search() throws Exception {
//		sc.setLnameSrch("Terces");
//	   
//	}
//
//	@Then("^User should found Name on the search table$")
//	public void user_should_found_Name_on_the_search_table() throws Exception {
//		sc.searchCustomerByFname("Victoria", "Terces");
//	   
//	}


	
	


}
