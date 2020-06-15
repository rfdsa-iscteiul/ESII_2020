package Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnalyticsFile {
	private final static String PATH_TO_FILE = "src/main/resources/Analytics.json";
	private JSONObject fileContent ;
	private DateTimeFormatter formatter;
	
	
	
	
	public AnalyticsFile() {
		 formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		try {
			JSONParser parser = new JSONParser();
			fileContent = (JSONObject)parser.parse(new FileReader(PATH_TO_FILE));
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public JSONObject getJson() {
		return fileContent;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void alterJson(boolean isUp, String keyname ) {
		System.out.println(fileContent.toJSONString());
		JSONObject aux = (JSONObject) fileContent.get(keyname);

		if( isUp ) {
			aux.put("Uptime",   Integer.parseInt( aux.get("Uptime").toString() ) +  2);
			aux.put("LastUp",   LocalDateTime.now().format(formatter));
			aux.put("isUp",1 );
			System.out.println("here");


		}else {
			aux.put("Downtime",   Integer.parseInt( aux.get("Downtime").toString() ) +  2);
			aux.put("isUp",0);


			
		}
		fileContent.put(keyname, aux);
		System.out.println(aux);
			
		
		
	}
	
	
public void updateFile() {
		
		try {
			FileWriter file = new FileWriter(PATH_TO_FILE);
			System.out.println(fileContent.toJSONString());

			file.write(fileContent.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//@SuppressWarnings("unchecked")
//public void alterAboutUs(boolean isUp) {
//	System.out.println(fileContent.toJSONString());
//	JSONObject aboutUs = (JSONObject) fileContent.get("AboutUs");
//
//	if( isUp ) {
//		aboutUs.put("Uptime",   Integer.parseInt( aboutUs.get("Uptime").toString() ) +  2);
//		aboutUs.put("LastUp",   LocalDateTime.now().format(formatter));
//
//	}else {
//		aboutUs.put("Uptime",   Integer.parseInt( aboutUs.get("Uptime").toString() ) +  2);
//		aboutUs.put("LastUp",   LocalDateTime.now().format(formatter));
//
//		
//	}
//	fileContent.put("AboutUs", aboutUs);
//	System.out.println(aboutUs);
//
//	
//	
//}
//	public void alterContactUs(boolean isUp) {
//			
//			
//	}
//	
//	public void alterCovidEvolution(boolean isUp) {
//		
//		
//	}
//	public void alterCovidQueries(boolean isUp) {
//		
//		
//	}
//	public void alterCovidScientificDisvoveries(boolean isUp) {
//		
//		
//	}
//	public void alterCovidSpread(boolean isUp) {
//		
//		
//	}
//	public void alterCovidWiki(boolean isUp) {
//		
//		
//	}
//	public void alterFAQ(boolean isUp) {
//		
//		
//	}
//	public void alterHome(boolean isUp) {
//		
//		
//	}
//	public void alterJoinUs(boolean isUp) {
//		
//		
//	}
//	public void alterLogIn(boolean isUp) {
//		
//		
//	}
//	public void WebSiteAnalytics(boolean isUp) {
//		
//		
//	}
	
	
	

}
