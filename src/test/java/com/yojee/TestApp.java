package com.yojee;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yojee.runner.App;

/**
 * Unit test for simple App.
 */
public class TestApp {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@Test
	public void testCsvInputInvalid() {
		App.main(new String[] { "invalid.csv", "true" });
		assertEquals("Input file invalid.csv doesn't exist\n", outContent.toString());
	}

	@Test
	public void testCsvInputValid() {
		App.main(new String[] { System.getProperty("user.dir") + "/src/test/java/com/yojee/valid.csv", "true" });
		assertEquals("1, book, 12.49\n1, music cd, 16.49\n1, chocolate bar, 0.85\n\nSales Taxes: 1.50\nTotal: 29.83\n",
				outContent.toString());
	}

	@Test
	public void testCsvOutput() {
		App.main(new String[] { System.getProperty("user.dir") + "/src/test/java/com/yojee/valid.csv" });
		File file = new File(System.getProperty("user.dir") + "/output.csv");
		assertEquals(true, file.exists());
		Path path = Paths.get(System.getProperty("user.dir") + "/output.csv");
		try {
			String orig = "1,book,12.49\r\n1,music cd,16.49\r\n1,chocolate bar,0.85\r\n\r\nSales Taxes: 1.50\r\nTotal: 29.83";
			String stringFromFile = new String(java.nio.file.Files.readAllBytes(path));
			assertEquals(orig, stringFromFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@After
	public void cleanUpStreams() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

}
