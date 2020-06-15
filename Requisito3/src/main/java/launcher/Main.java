package launcher;

import extractor.MetaDataExtractor;

/**
 * This class servers as the launcher for the java application
 * Some predefined fields are present and were used in testing, can be changed later
 * @author Nuno Lobato
 *
 */
public class Main {

	/**
	 * path for the repository of files to be used, must include separator at the end
	 */
	public static final String REPO_PATH = "D:/Nuno/COVID19/";
	
	/**
	 * path for the directory where both html model file and resulting html file are located, must include separator at the end
	 */
	public static final String HTML_PATH = "";
	
	/**
	 * extension of file to be listed and used
	 */
	public static final String FILE_EXTENSION = ".pdf";
	
	
	/**
	 * Calls all other methods to get metadata from all files of specified type and create html table with relevant info from the files metadata
	 * Resulting html table is based on a model html file
	 * Paths must include separator at the end
	 * @param args - has to have 3 arguments: "repository_path" "html_path" "file_extension"
	 * repository_path - path for repository where files are stored
	 * html_path - path for tableModel.html file and where table.html file is created
	 * file_extension - type of file to get metadata from (use ".pdf")
	 */
	public static void main(String[] args) {

		String reppath, htmlpath, fileextension;
		if(args.length != 3) {
			reppath = REPO_PATH; 
			htmlpath = HTML_PATH; 
			fileextension = FILE_EXTENSION; 
		}
		else {
			reppath = args[0]; 
			htmlpath = args[1]; 
			fileextension = args[2]; 
		}
		MetaDataExtractor me = new MetaDataExtractor();
		me.metadataToHtml(reppath, htmlpath, fileextension);
	}

}