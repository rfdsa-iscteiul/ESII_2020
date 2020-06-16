package pages;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;


import Utils.AnalyticsFile;
import Utils.Email;

/**
 * @author anamartacontente
 *
 */
public class LogIn extends Page {
	


	private final static String  URL =  "https://fenix.iscte-iul.pt/loginPage.jsp";
	private final static String PAGE_TITLE = "Login - ISCTE-IUL - ISCTE - Instituto Universitário de Lisboa";
	private final static String PAGE_AFTER_LOGIN = "Comunicação - ISCTE-IUL - ISCTE - Instituto Universitário de Lisboa";
	private final static String JSONKEYNAME = "LogIn";
	private By usernameBy = By.name("username");
	private By passwordBy = By.name("password");
	private By loginBy = By.name("ok");
	private String username;
	private String password;



	  
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public LogIn( AnalyticsFile jsonFile) {
		super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
	}
	
	  /**
	 *Tests If page is available in case it is it will call testLoginValidUser it will send an email otherwise
	 */
	@Override
	  public void testPage() {
			 
		  driver.get(url);
		  if( driver.getTitle().contentEquals( pageTitle)    ) {
			  System.out.println("Success");
			  testloginValidUser();
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
	public void testloginValidUser() {
		    
			driver.findElement(usernameBy).sendKeys(username);
			System.out.println(driver.findElement(usernameBy));
		    driver.findElement(passwordBy).sendKeys(password);
		    driver.findElement(loginBy).click();
		    waitForLoad();
		    
		    if( driver.getTitle().contentEquals( PAGE_AFTER_LOGIN)  ) {
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
		 username=p.getProperty("email");
		 password=p.getProperty("password");

		  
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
	

}
