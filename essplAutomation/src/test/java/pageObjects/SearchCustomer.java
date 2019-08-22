package pageObjects;

import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utiilities.WaitHelper;

public class SearchCustomer {
	
	public WebDriver ldriver;
	WaitHelper wh;
	
	public SearchCustomer(WebDriver rdriver) {
		ldriver = rdriver ;
		PageFactory.initElements(ldriver,this);//
		wh = new WaitHelper(ldriver);
	}
	
	@FindBy(xpath="//input[@id='SearchEmail']")
	@CacheLookup
	WebElement SrchEmail;
	
	@FindBy(xpath="//input[@id='SearchCompany']")
	@CacheLookup
	WebElement SrchCompany;
	
	@FindBy(xpath="//input[@id='SearchFirstName']")
	WebElement SrchEntrFstName;
	
	@FindBy(id="SearchLastName")
	@CacheLookup
	WebElement SrchLastName;
	
	@FindBy(xpath="//input[@id='SearchIpAddress']")
	@CacheLookup
	WebElement SrchIpAddress;
	
	@FindBy(xpath="//button[@id='search-customers']")
	@CacheLookup
	WebElement ClksrchCustomers;
	
	@FindBy(xpath="//table[@id='customers-grid']")
	@CacheLookup
	WebElement table;
	
	@FindBy(xpath="//table[@id='customers-grid']//tbody/tr")
	@CacheLookup
	List<WebElement> tbleRows;
	
	@FindBy(xpath="//table[@id='customers-grid']//tbody/tr/td")
	@CacheLookup
	List<WebElement> tbleColumns;
	
	//By SrchEmail = By.("//input[@id='SearchEmail']");//
	//By SrchCompany = By.xpath("//input[@id='SearchCompany']");
	//By SrchFirstName = By.xpath("//input[@id='SearchFirstName']");
	//By SrchLastName = By.xpath("//input[@id='SearchLastName']");
	//By SrchIpAddress = By.xpath("//input[@id='SearchIpAddress']");
	//By srchCustomers = By.xpath("//button[@id='search-customers']");
	
	public void setEmailSrch(String email) {
		wh.WaitForElement(SrchEmail, 30);
		SrchEmail.clear();
		SrchEmail.sendKeys(email);
	}
	
	public void setCompanySrch(String company) {
		SrchCompany.clear();
		SrchCompany.sendKeys(company);
	}
	
	public void setFnameSrch(String fsname) {
		wh.WaitForElement(SrchEntrFstName, 50);
		SrchEntrFstName.clear();
		SrchEntrFstName.sendKeys(fsname);
	}
	
	public void setLnameSrch(String Lname) {
		SrchLastName.clear();
		SrchLastName.sendKeys(Lname);
	}
	
	public void setIpAddressSrch(String IpAdrs) {
		SrchIpAddress.clear();
		SrchIpAddress.sendKeys(IpAdrs);
	}
	
	public void clkSrchmailbtn() {
		ClksrchCustomers.click();
	}
	
	public int getNoOfRows() {
		return(tbleRows.size());
	}
	
	public int getNoOfColumns() {
		return(tbleColumns.size());
	}
	
	public boolean searchCustomerByEmail(String email) {
		boolean flag = false ;
		for(int i=1;i<=getNoOfRows();i++) {
			String emailIDs =table.findElement(By.xpath("//table[@id='customers-grid']//tbody/tr['+i+']/td[2]")).getText();
			System.out.println(emailIDs);
			if(emailIDs.equals(email)) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean searchCustomerByFname(String Fname ,String Lname) {
		boolean flag = false ;
		for(int i=1;i<=getNoOfRows();i++) {
			String name =table.findElement(By.xpath("//table[@id='customers-grid']//tbody/tr['+i+']/td[3]")).getText();
			String names[] = name.split(" "); 
			System.out.println(name);
			System.out.println(Fname);
			if(names[0].equals(Fname) && names[1].equals(Lname)) {
				flag = true;
			}
		}
		return flag;
	}

}
