package pages;


import Utils.AnalyticsFile;

/**
 * @author anamartacontente
 *
 */
public class CovidSpread extends Page{
	private final static String  URL =  "https://fenix.iscte-iul.pt/loginPage.jsp";
	private final static String PAGE_TITLE = "Login - ISCTE-IUL - ISCTE - Instituto Universitário de Lisboa";
	private final static String JSONKEYNAME = "CovidSpread";



	  
	  /**
	 * @param jsonFile object that deals with analytics
	 */
	public CovidSpread(AnalyticsFile jsonFile) {
			super(URL, PAGE_TITLE,JSONKEYNAME, jsonFile);
		}
	

}
