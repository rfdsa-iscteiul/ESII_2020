package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class CovidQueries extends Page{
	private final static String  URL =  "http://192.168.99.100/covid-spread-covid-queries/";
	private final static String PAGE_TITLE = "Covid Queries";
	private final static String JSONKEYNAME = "CovidQueries";

	  public CovidQueries(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
	  }
	

}
