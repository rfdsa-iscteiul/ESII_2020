package Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author anamartacontente
 *
 */
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
	/**
	 * @return JSONObject that represents the analytics of each page
	 */
	public JSONObject getJson() {
		return fileContent;
	}
	
	
	
	/** Updates json object with the analytics of a page
	 * @param isUp boolean that tells us if the up is working
	 * @param keyname key that represents page on the json array
	 */
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
	
	
/**
 * Writes file with the updated Json object representing the analytics of each page
 */
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


	
	
	

}
