package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	public WebDriver ldriver;
	
	public LoginPage(WebDriver rdriver) {
		ldriver = rdriver ;
		PageFactory.initElements(rdriver,this);
	}
	
	@FindBy(id="Email")
	@CacheLookup
	WebElement txtLogins;  
	
	@FindBy(id="Password")
	@CacheLookup
	WebElement txtPasswds;
	
	@FindBy(className="login-button")
	@CacheLookup
	WebElement btnSubmt;
	
	@FindBy(className="account-info")
	@CacheLookup
	WebElement myAccntName;
	
	@FindBy(xpath="//*[contains(@href,'logout')]")
	@CacheLookup
	WebElement logout;
	
	public void setUserName(String Uname) {
		System.out.println("enter into uname");
		txtLogins.clear();
		System.out.println("Clear");
		txtLogins.sendKeys(Uname);
	}
	
	public void setPassword(String Passwd) {
		txtPasswds.clear();
		txtPasswds.sendKeys(Passwd);
	}
	
	public void clkSubmit() {
		btnSubmt.click();
	}
	
	public void logout() throws Exception {
//		myAccnt.click();
//		Thread.sleep(100);
		logout.click();
		
	}
}
