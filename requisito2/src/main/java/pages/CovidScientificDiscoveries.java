package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class CovidScientificDiscoveries extends Page {
	private final static String  URL =  "http://192.168.99.100/covid-scientific-discoveries/";
	private final static String PAGE_TITLE = "Covid Scientific Discoveries";
	private final static String JSONKEYNAME = "CovidScientificDiscoveries";


	  
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public CovidScientificDiscoveries(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	

}
