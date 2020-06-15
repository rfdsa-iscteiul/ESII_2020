package pages;

import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class AboutUs extends Page{
	private final static String  URL =  "l.com";
	//private final static String  URL =  "http://192.168.99.100/about/";
	private final static String PAGE_TITLE = "About Us";
	private final static String JSONKEYNAME = "AboutUs";

	
	  public AboutUs( AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		
	}
	

}
