package requisito6;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Jteste {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CovidEvolutionDiff c = new CovidEvolutionDiff();
		String gitUrl = "https://github.com/vbasto-iscte/ESII1920.git";
		String repoPath = "src/test/resources";
		String fileToSearch = "covid19spreading.rdf";
		
		c.setGitUrl(gitUrl, repoPath, fileToSearch);
		assertEquals(c.getGitUrl(),"https://github.com/vbasto-iscte/ESII1920.git");
	}
	
	@Test
	public void tesat() {
		CovidEvolutionDiff c = new CovidEvolutionDiff();
		String gitUrl = "https://github.com/vbasto-iscte/ESII1920.git";
		String repoPath = "src/test/resources";
		String fileToSearch = "covid19spreading.rdf";
		
		c.setGitUrl(gitUrl, repoPath, fileToSearch);
		
		int a = c.runTest();
		assertEquals(1, a);
	}

}
