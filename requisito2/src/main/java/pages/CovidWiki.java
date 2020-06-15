package pages;

import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class CovidWiki extends Page {
	private final static String  URL =  "http://192.168.99.100/covid-wiki/";
	private final static String PAGE_TITLE = "Covid Wiki";
	private final static String JSONKEYNAME = "CovidWiki";

	
	  public CovidWiki(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
		
	

}
