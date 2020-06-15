package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class CovidEvolution extends Page{
	private final static String  URL = " http://192.168.99.100/covid-evolution/";
	private final static String PAGE_TITLE = "Covid Evolution";
	private final static String JSONKEYNAME = "CovidEvolution";



	  public CovidEvolution(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
	}
	

}
