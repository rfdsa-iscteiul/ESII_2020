package pages;

import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class FAQ extends Page{
	private final static String  URL =  "http://192.168.99.100/faq/";
	private final static String PAGE_TITLE = "FAQ";
	private final static String JSONKEYNAME = "FAQ";

	


	  
	  public FAQ(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		
	}
	

}
