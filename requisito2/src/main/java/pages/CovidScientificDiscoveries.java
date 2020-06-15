package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class CovidScientificDiscoveries extends Page {
	private final static String  URL =  "http://192.168.99.100/covid-scientific-discoveries/";
	private final static String PAGE_TITLE = "Covid Scientific Discoveries";
	private final static String JSONKEYNAME = "CovidScientificDiscoveries";





	  
	  public CovidScientificDiscoveries(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	

}
