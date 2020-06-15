package extractor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import launcher.Main;

class MetaDataExtractorTest {
	
	private static MetaDataExtractor me = new MetaDataExtractor();;
	private static String[] files = me.listFiles(Main.REPO_PATH, Main.FILE_EXTENSION);

	
	@Test
	void testListFiles() {
		String[] files = me.listFiles(Main.REPO_PATH, Main.FILE_EXTENSION);
		assertNotNull(files);
		assertEquals(files.length, 4);
	}
	
	@Test
	void testExtractMetaData() {
		me.extractMetaData("D:/Nuno/COVID199/", files[0]);
		assertNotNull(me.extractMetaData(Main.REPO_PATH, files[0]));
		
	}
	
	@Test
	void testBuildTableContent() {
		String s = me.buildTableContent(Main.REPO_PATH, files);
		assertNotNull(s);
		assertNotEquals(s, "");
	}
	
	@Test
	void testBuildhtml() {
		String s = me.buildTableContent(Main.REPO_PATH, files);
		me.buildhtml(Main.HTML_PATH, s);
		File html = new File(Main.HTML_PATH + "table.html");
		me.buildhtml("D:/Nuno/COVID199/", s);
		assertNotNull(html);
		
	}
	
	@Test
	void testMetadataToHtml() {
		me.metadataToHtml(Main.REPO_PATH, Main.HTML_PATH, Main.FILE_EXTENSION);
	}

}
