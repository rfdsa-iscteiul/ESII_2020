package launcher;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testMain() {
		Main m = new Main();
		assertNotNull(m);
		String[] args1 = {};
		Main.main(args1);
		String[] args = {"D:/Nuno/COVID19/", "", ".pdf"};
		Main.main(args);
		assertNotNull(new File(Main.HTML_PATH + "table.html"));
	}

}
