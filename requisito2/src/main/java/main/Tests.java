package main;

import Utils.AnalyticsFile;
import Utils.WriteHtmlFile;
import pages.AboutUs;
import pages.CovidEvolution;
import pages.CovidQueries;
import pages.CovidScientificDiscoveries;
import pages.CovidSpread;
import pages.CovidWiki;
import pages.FAQ;
import pages.Home;
import pages.JoinUs;
import pages.LogIn;
import pages.WebSiteAnalytics;

public class Tests {
	public static void main(String[] args) {
		AnalyticsFile a = new AnalyticsFile();
		
		new AboutUs(a).test();
		new LogIn(a).test();
//    	new CovidEvolution( a).test();
//    	new CovidQueries( a).test();
//    	new CovidScientificDiscoveries( a).test();
//    	new CovidSpread( a).test();
//    	new CovidWiki( a).test();
//    	new FAQ( a).test();
//    	new Home(a).testPage();
//    	new JoinUs(a).testPage();
//    	new WebSiteAnalytics( a).testPage();
    	
    	a.updateFile();
    	new WriteHtmlFile().updateHTMLFile();
	}

}
