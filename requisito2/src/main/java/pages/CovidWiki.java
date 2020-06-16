package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class CovidWiki extends Page {
	private final static String  URL =  "http://192.168.99.100/covid-wiki/";
	private final static String PAGE_TITLE = "Covid Wiki";
	private final static String JSONKEYNAME = "CovidWiki";

	
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public CovidWiki(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
		
	

}
