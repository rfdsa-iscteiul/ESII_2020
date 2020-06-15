package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.AnalyticsFile;

public class JoinUs extends Page {
	private final static String  URL =  "https://www.facebook.com/";
	private final static String PAGE_TITLE = "Facebook – log in or sign up";
	private final static String JSONKEYNAME = "JoinUs";

//	private By firstnameBy = By.name("firstname");
//	private By lastnamedBy = By.name("lastname");
//	private By emailBy = By.name("reg_email__");
//	private By passwordBy = By.name("reg_passwd__");
	private By by = By.name("reg_email__");

	//private By loginBy = By.name("ok");

	


	  

	
	  public JoinUs(AnalyticsFile jsonFile) {
			super(URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
		
//	
//	  public void testJoinUsUserExists(String userName, String password) {
//		  WebDriverWait waiter = new WebDriverWait(driver, 5000);
//		  waiter.until( ExpectedConditions.presenceOfElementLocated(by) );
//		  driver.FindElement(by);
//		    
//		    
//		  }  
	  
	  
	  
	
}
