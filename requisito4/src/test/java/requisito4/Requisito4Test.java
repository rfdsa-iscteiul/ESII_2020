package requisito4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class Requisito4Test {

	@Test
	void testAll() {
		Requisito4.main(null);
	}
	
	@Test
	void testClone() {
		Requisito4.cloneRep();
		assertNotNull(new File("src/main/resources").list().length);
	}
	
}
