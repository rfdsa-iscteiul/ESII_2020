package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class Home extends Page{
	private final static String  URL =  "http://192.168.99.100/";
	private final static String PAGE_TITLE = "ESII “Complemento do projeto”";
	private final static String JSONKEYNAME = "Home";

	
	  public Home(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
		
	
		
}
