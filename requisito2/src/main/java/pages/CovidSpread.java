package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utils.AnalyticsFile;

public class CovidSpread extends Page{
	private final static String  URL =  "https://fenix.iscte-iul.pt/loginPage.jsp";
	private final static String PAGE_TITLE = "Login - ISCTE-IUL - ISCTE - Instituto Universitário de Lisboa";
	private final static String JSONKEYNAME = "CovidSpread";



	  
	  public CovidSpread(AnalyticsFile jsonFile) {
			super(URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	

}
