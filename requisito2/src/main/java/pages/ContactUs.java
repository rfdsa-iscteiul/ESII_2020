package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class ContactUs extends Page{
	private final static String  URL =  "http://192.168.99.100/contact/";
	private final static String PAGE_TITLE = "Contact";
	private final static String JSONKEYNAME = "ContactUs";
	
	private By usernameBy = By.name("your-name");
	private By passwordBy = By.name("your-email");
	private By loginBy = By.name("your-subject");
	private By logisas = By.name("your-message");
	private By loginjy = By.name("your-subject");
	private By lButton = By.cssSelector("#wpcf7-f5-p35-o1 > form > p:nth-child(6) > input");



	
	  
	  public ContactUs(WebDriver driver, AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	
//	  public void testSendEmail() {
//		    driver.findElement(usernameBy).sendKeys(userName);
//		    driver.findElement(passwordBy).sendKeys(password);
//		    driver.findElement(loginBy).click();
//		    waitForLoad();
//		    if( driver.getTitle().contentEquals( PAGE_AFTER_LOGIN)  ) {
//		    	System.out.println("worked");
//		    	
//		    }else {
//		    	System.out.println("failed " + driver.getTitle());
//		    	
//		    }
//		    
//		    
//		  }  
}
