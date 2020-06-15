package extractor;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

/**
 * This class uses some methods to:
 * -List all files of a specific type that exist in a directory;
 * -Extract each file's metadata;
 * -Create a String to populate an html table with specific info from each file's metadata
 * -Create a new html (table.html) file from a pre-existing template called tableModel.html with populated html table
 * @author Nuno Lobato
 *
 */
public class MetaDataExtractor {

	
	/**
	 * Get all file names of specified type (extension) from specified folder into a String array
	 * @param repPath - path to the repository/folder where files are stored
	 * @param fileExtension - extension of wanted files (eg.: ".pdf")
	 * @return String[] with names of files
	 */
	public String[] listFiles (String repPath, String fileExtension) {
		File dir = new File(repPath);
		String [] files = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(fileExtension);
			}
		});
		return files;
	}
	
	
	
	/**
	 * Get metadata of specified file 
	 * @param repPath - path to the repository/folder where file is stored
	 * @param file - file to extract metadata from
	 * @return DocumentMetadata specified file's metadata formated to DocumentMetadata object as specified on CERMINE java library 
	 */
	public DocumentMetadata extractMetaData (String repPath, String file) {
		ContentExtractor extractor;
		DocumentMetadata data = null;
		InputStream input;
		try {
			input = new FileInputStream(repPath + file);
			extractor = new ContentExtractor();
			extractor.setPDF(input);
			data = extractor.getMetadata();
		} catch (IOException | TimeoutException | AnalysisException e) {
			System.err.println("error analysing or accessing file");
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	/**
	 * Using StringBuilder class to append Strings, create String with html format containing all information from given files metadata to be used as content of an html table
	 * Selects relevant info (Title, Journal and Publish year) from files metadata creates String as if populating a 3 column html table, with each row representing each file's info
	 * @param repPath - path to the repository/folder where files to be used are stored
	 * @param files - String array of all file names to be used for metadata extraction
	 * @return String of html code containing table content, including tr and td html tags
	 */
	public String buildTableContent (String repPath, String[] files) {
		StringBuilder strb = new StringBuilder();
		for (String file: files) {
			DocumentMetadata data = extractMetaData(repPath, file);
			strb.append("<tr><td><a href=\"" + repPath + file + "\" target=\"_blank\">")
			.append(data.getTitle().toString())
			.append("</a></td><td>")
			.append(data.getJournal().toString())
			.append("</td><td>")
			.append(data.getDate(DateType.PUBLISHED).getYear().toString())
			.append("</td></tr>");
		}
		return strb.toString();
	}
	
	
	
	/**
	 * Write new html file based on an existing html file
	 * Model file should be an html table with table contents as a variable to be substituted ($content)
	 * @param htmlPath - path where model html file is located and where resulting html file is stored
	 * @param content - String of html code containing table content, including tr and td html tags
	 */
	public void buildhtml (String htmlPath, String content) {
		File htmlTemplateFile = new File(htmlPath + "tableModel.html");
		File html = new File(htmlPath + "table.html");
		try {
			String htmlasString = FileUtils.readFileToString(htmlTemplateFile, Charset.forName("UTF-8"));
			htmlasString = htmlasString.replace("$content", content);
			FileUtils.writeStringToFile(html, htmlasString, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("error accessing file");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Get relevant information from the metadata of each file on specified path and build an html table with that information
	 * Resulting html table is based on a model html file
	 * Paths must include separator at the end
	 * @param repPath - path to the repository/folder where files are stored
	 * @param htmlPath - path where model html file is located and where resulting html file is stored
	 * @param fileExtension - extension of wanted files (eg.: ".pdf")
	 */
	public void metadataToHtml(String repPath,String htmlPath, String fileExtension) {
		String[] files = listFiles(repPath, fileExtension);
		String content = buildTableContent(repPath, files);
		buildhtml(htmlPath, content);
	}

}