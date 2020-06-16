package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

/**
 * @author anamartacontente
 *
 */
public class WriteHtmlFile {
	private static final String PATH_TO_TEMPLATE = "src/main/resources/AnalyticsTemplate.html";
	private static final String PATH_TO_FILE = "src/main/resources/Analytics.html";
	private JSONObject content;
	

	
	public WriteHtmlFile() {
		content = new AnalyticsFile().getJson();
		
	}
	
	
	/**
	 * @param htmlString String that was read from the template
	 * @return String with variables replaced
	 */
	private String replaceVariables(String htmlString ) {
		int i = 0;
	    for (Object key : content.keySet()) {
	    	
	    	 htmlString = htmlString.replace("$upTime" + 	i + "_", ((JSONObject) content.get(key)).get("Uptime").toString()    );
	    	 htmlString = htmlString.replace("$downTime" + i + "_", ((JSONObject) content.get(key)).get("Downtime").toString());
	    	 String isUp =((JSONObject) content.get(key)).get("isUp").toString();
	    	 
	    	 if(isUp.equals("0")) {
	    		 
	    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        	 LocalDateTime dateTime = LocalDateTime.parse(( ( (JSONObject)content.get(key)).get("LastUp")).toString() , formatter);
	        	 htmlString = htmlString.replace("$lastUp"+ i + "_"," Is down since " + dateTime.plusHours(2).format(formatter) );
	    	 }else {
	          	 htmlString = htmlString.replace("$lastUp" + i + "_","" );
	    		 
	    	 }
	    	 i++;
	    }
	    return htmlString;
		
	}
	/**
	 *Writes an HTML file  based on a template and a JsonObject
	 */
	@SuppressWarnings("deprecation")
	public void updateHTMLFile() {
	
	File htmlTemplateFile = new File(PATH_TO_TEMPLATE);
    String htmlString = "Content-type: text/html\n\n";
	try {
		htmlString += FileUtils.readFileToString(htmlTemplateFile);
		htmlString = replaceVariables(htmlString);
		//System.out.println(htmlString);

		writeHTMLFile(htmlString);
		//System.out.println(htmlString);

		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
		
	/**
	 * Writes HTML file from string
	 * @param htmlString string to be written
	 */
	private void writeHTMLFile(String htmlString) {
		File resultFile = new File(PATH_TO_FILE);
		PrintWriter pw;
		try {
			pw = new PrintWriter(resultFile,"utf-8");
			pw.write(htmlString);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	

	
    
    
}
