package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class FAQ extends Page{
	private final static String  URL =  "http://192.168.99.100/faq/";
	private final static String PAGE_TITLE = "FAQ";
	private final static String JSONKEYNAME = "FAQ";

	  
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public FAQ(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		
	}
	

}
