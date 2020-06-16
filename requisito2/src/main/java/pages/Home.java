package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class Home extends Page{
	private final static String  URL =  "http://192.168.99.100/";
	private final static String PAGE_TITLE = "ESII “Complemento do projeto”";
	private final static String JSONKEYNAME = "Home";

	
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public Home(AnalyticsFile jsonFile) {
			super( URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
		
	
		
}
