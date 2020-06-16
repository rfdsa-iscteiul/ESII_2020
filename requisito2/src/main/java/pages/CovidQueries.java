package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class CovidQueries extends Page{
	private final static String  URL =  "http://192.168.99.100/covid-spread-covid-queries/";
	private final static String PAGE_TITLE = "Covid Queries";
	private final static String JSONKEYNAME = "CovidQueries";

	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public CovidQueries(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
	  }
	

}
