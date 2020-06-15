package pages;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.AnalyticsFile;
import Utils.Email;


public class Page {
	
	  protected  WebDriver driver;
	  protected String url;
	  protected String pageTitle;
	  private String JsonKeyName;
	  protected AnalyticsFile jsonFile;

	
	  public Page( String url, String pageTitle, String JsonKeyName, AnalyticsFile jsonFile){

		
	    this.url = url;
	    this.pageTitle=pageTitle;
	    this.JsonKeyName= JsonKeyName;
	    this.jsonFile = jsonFile;
	    
	    
	  }
	  public void setUp() throws Exception {
		  System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		  driver = new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  
	  }
	  
	  public void testPage() {
		  
	
		  driver.get(url);
		  
		  if( driver.getTitle().contentEquals( pageTitle)    ) {
			  System.out.println("Success");
			  jsonFile.alterJson(true, JsonKeyName);
		  }else {
			  System.out.println( driver.getTitle() );
			  System.out.println("Failed");
			  jsonFile.alterJson(false, JsonKeyName);
			  Email.sendEmail("Page down", pageTitle + " is unavailable");

		  } 
			  
	
			  
		  
	  }
	  public void test() {
		  try {
			setUp();
			testPage();
		} catch (Exception e) {
			jsonFile.alterJson(false, JsonKeyName);
			Email.sendEmail("Page down", pageTitle + " is unavailable");
		}finally{
			driver.close();
			
		}
		  
		  
	  }
	  
	  
	  
	  
	  public void waitForLoad() {
	        ExpectedCondition<Boolean> pageLoadCondition = new
	                ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver driver) {
	                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	                    }
	                };
	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        wait.until(pageLoadCondition);
	    }

			
}



