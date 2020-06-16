package pages;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import Utils.AnalyticsFile;
import Utils.Email;

/**
 * @author anamartacontente
 *
 */
public class ContactUs extends Page{
	private final static String  URL =  "http://automationpractice.com/index.php?controller=contact";
//	private final static String  URL =  "http://192.168.99.100/contact/";
//	private final static String PAGE_TITLE = "Contact";
	private final static String PAGE_TITLE = "Contact us - My Store";
	
	private final static String JSONKEYNAME = "ContactUs";
//	private By usernameBy = By.name("your-name");
//	private By emailBy = By.name("your-email");
//	private By subjectBy = By.name("your-subject");
//	private By messageBy = By.name("your-message");
//	private By buttonBy = By.cssSelector("#wpcf7-f5-p35-o1 > form > p:nth-child(6) > input");
//	private By successBy = By.cssSelector("div.wpcf7-response-output.wpcf7-display-none.wpcf7-mail-sent-ng");
//	private String successText = "Email Sent";

	private By emailBy = By.name("from");
	private By subjectBy = By.name("id_contact");
	private By messageBy = By.name("message");
	private String subjectText = "Webmaster";
	private By buttonBy = By.name("submitMessage");
	private By successBy = By.cssSelector(".alert");
	private String successText ="Your message has been successfully sent to our team.";

	
	
	
	private String username;
	private String email ;
	private String subject="Test"; 
	private String message="Just Testing" ;
	
	



	
	  
	 /**
	 * 
	 * @param jsonFile object that deals with analytics
	 */
	public ContactUs(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	  
	  

	

	  
	 /**
	 * Tests If page is available in case it is it will call testContactUs it will send an email otherwise
	 */
	@Override
	  public void testPage() {
			 
		  driver.get(url);
		  if( driver.getTitle().contentEquals( pageTitle)    ) {
			  System.out.println("Success");
			  testContactUs();
		  }else {
			  System.out.println( driver.getTitle() );
			  System.out.println("Failed");
			  jsonFile.alterJson(false, JSONKEYNAME);
			  Email.sendEmail("Page down", pageTitle + " is unavailable");

		  }
			  
		  
	  }
	

	  
	  /**
	 * Tests if it is possible to use the function to contact it will send an email otherwise
	 */
	public void testContactUs() {
		 
		    
		    driver.findElement(emailBy).sendKeys(email);
		    driver.findElement(messageBy).sendKeys(message);
		    Select subject = new Select(driver.findElement(subjectBy));
		    subject.selectByVisibleText(subjectText);
		    driver.findElement(buttonBy).click();
		    waitForLoad();
		    
		    if( driver.findElement(successBy).getText().equals(successText)) {
		    	System.out.println("worked");
				 jsonFile.alterJson(true, JSONKEYNAME);
				 

		    	
		    }else {
		    	System.out.println("failed " + driver.getTitle());
		    	jsonFile.alterJson(false, JSONKEYNAME);
				Email.sendEmail("Page down", pageTitle + " is unavailable");

		    	
		    }
		
	    
	 }
	
	/**
	 * Sets Up driver for testing and gets variables for testing from config.ini file
	 */
	@Override
	public void setUp() throws Exception {
		 super.setUp();
		 Properties p = new Properties();
		 p.load(new FileInputStream("src/main/resources/config.ini"));
		 username=p.getProperty("username");
		 email=p.getProperty("emailii");

		  
	  }
	
	/**
	 * Sets Up the test and tests it
	 */
	@Override
	public void test() {
		try {
			setUp();
			testPage();
			
		} catch (Exception e) {
			jsonFile.alterJson(false, JSONKEYNAME);
			Email.sendEmail("Page down", pageTitle + " is unavailable");
		}finally{
			driver.close();
			
		}
	}
	
	
//	public void testContactUs() {
//    
//	driver.findElement(usernameBy).sendKeys(username);
//    driver.findElement(emailBy).sendKeys(email);
//    driver.findElement(subjectBy).sendKeys(subject);
//    driver.findElement(messageBy).sendKeys(message);
//    driver.findElement(buttonBy).click();
//    waitForLoad();
//    
//    if( driver.findElement(successBy).getText().equals(successText)) {
//    	System.out.println("worked");
//		 jsonFile.alterJson(true, JSONKEYNAME);
//
//    	
//    }else {
//    	System.out.println("failed " + driver.getTitle());
//    	jsonFile.alterJson(false, JSONKEYNAME);
//		Email.sendEmail("Page down", pageTitle + " is unavailable");
//
//    	
//    }
//
//
//}
//public void testSendEmail() {
//driver.findElement(usernameBy).sendKeys(userName);
//driver.findElement(passwordBy).sendKeys(password);
//driver.findElement(loginBy).click();
//waitForLoad();
//if( driver.getTitle().contentEquals( PAGE_AFTER_LOGIN)  ) {
//System.out.println("worked");
//
//}else {
//System.out.println("failed " + driver.getTitle());
//
//}
//
//
//}  
}
