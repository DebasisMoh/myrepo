package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CustomerOperation {
	
public WebDriver ldriver;
	
	public CustomerOperation(WebDriver rdriver) {
		ldriver = rdriver ;
		PageFactory.initElements(rdriver,this);
	}
	
	By Customers_menu = By.xpath("//a[@href='#']//span[contains(text(),'Customers')]");
	By Customers_menuitem = By.xpath("//span[@class='menu-item-title'][contains(text(),'Customers')]");
	By btnAddNew = By.xpath("//a[@class='btn bg-blue']");
	By txtEmail = By.xpath("//input[@id='Email']");
	By txtPassword = By.xpath("//input[@id='Password']");
	By txtCustomerRole = By.xpath("//div[@class='']");
	By txtFname = By.xpath("//input[@id='FirstName']");
	By txtLname = By.xpath("//input[@id='LastName']");
	By txtGenderM = By.xpath("//input[@id='Gender_Male']");
	By txtGenderF = By.xpath("//input[@id='Gender_Female']");
	By txtDOB = By.xpath("//input[@id='DateOfBirth']");
	By txtExmptTAX = By.xpath("//input[@id='IsTaxExempt']");
//	By txtnewsletterStoreName = By.name("//input[@id='SelectedNewsletterSubscriptionStoreIds']");
//	By txtnewsletterStoreTest = By.name("//input[@id='SelectedNewsletterSubscriptionStoreIds']");
	By txtcustRoles = By.xpath("//div[@class='k-multiselect-wrap k-floatwrap']");
	By lstAdminstrtor = By.xpath("//li[contains(text(),'Administrators')]");
	By lstForemModertors = By.xpath("//li[contains(text(),'Forum Moderators')]");
	By lstGuest = By.xpath("//li[contains(text(),'Guests')]");
	By lstMrktng = By.xpath("//li[contains(text(),'marketing')]");
	By lstRegstrd = By.xpath("//li[contains(text(),'Registered')]");
	By lstVendrs = By.xpath("//li[contains(text(),'Vendors')]");
	By drpdwnMgrOfVendrs = By.xpath("//li[contains(text(),'Vendors')]");
	By txtAreaAdminContnt = By.xpath("//textarea[@id='AdminComment']");
	By btnSave = By.xpath("//button[@name='save']");
	By btnSaveContinue = By.xpath("//button[@name='save-continue']");//Company
	By CmpnyName = By.xpath("//input[@id='Company']");
	
	//Action Methods
	
	public String getPageTitle() {
		return ldriver.getTitle();
	}
	
	
	public void clickOnCustomersMenu() {
		ldriver.findElement(Customers_menu).click();
	}
	
	public void clickonCustomerMenuItem() {
		ldriver.findElement(Customers_menuitem).click();
	}
	
	public void clickOnAddNew() {
		ldriver.findElement(btnAddNew).click();
	}
	
	public void setFname(String fName) {
		ldriver.findElement(txtFname).sendKeys(fName);
	}
	
	public void setLname(String lName) {
		ldriver.findElement(txtLname).sendKeys(lName);
	}
	
	public void setPasswrd(String pswd) {
		ldriver.findElement(txtPassword).sendKeys(pswd);
	}
	
	public void setEmail(String email) {
		ldriver.findElement(txtEmail).sendKeys(email);
	}
	
	public void setDOB(String dob) {
		ldriver.findElement(txtDOB).sendKeys(dob);
	}
	
	public void setCompnyName(String CompnyName) {
		ldriver.findElement(CmpnyName).sendKeys(CompnyName);
	}
	
	public void save() {
		ldriver.findElement(btnSave).click();
	}
	
	public void setCustomerRoles(String role) {
		WebElement listItem ;
		if(role.equals("Administrators")) {
			listItem = ldriver.findElement(lstAdminstrtor);
		}
		else if (role.equals("Forum Moderators")) {
			listItem = ldriver.findElement(lstForemModertors);
		}
		else if (role.equals("Guests")) {
			listItem = ldriver.findElement(lstGuest);
		}
		else if (role.equals("marketing")) {
			listItem = ldriver.findElement(lstMrktng);
		}
		else if (role.equals("Registered")) {
			listItem = ldriver.findElement(lstRegstrd);
		}
		else if (role.equals("Vendors")) {
			listItem = ldriver.findElement(lstVendrs);
		}
		else
		{
			listItem = ldriver.findElement(lstGuest);
		}
		listItem.click();
		
		JavascriptExecutor js = (JavascriptExecutor)ldriver ;
		js.executeScript("arguments[0].click()", listItem);
	}
	
	public void managerOfVendor(String Value) {
		Select drpwn = new Select(ldriver.findElement(drpdwnMgrOfVendrs));
		drpwn.selectByVisibleText(Value);
	}
	
	public void setGender(String gender) {
		
		if(gender.equals("Male")) {
			ldriver.findElement(txtGenderM).click();
		}
		else if (gender.equals("Female")) {
			ldriver.findElement(txtGenderF).click();
		}
		else
		{
			ldriver.findElement(txtGenderM).click();
		}
	}
	
	
	
}
