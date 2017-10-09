package com.yojee.executor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.yojee.calculator.SalesTaxCalculator;
import com.yojee.model.Item;

public class FileExecutor implements ExecutorService {

	private String input_file;
	private boolean console = true;
	public FileExecutor(String fileName,boolean console) {
		this.input_file = fileName;
		this.console = console;
	}

	public void execute() {
		Scanner sc = null;
		try {
			Reader in = new FileReader(input_file);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			SalesTaxCalculator taxCalculator = new SalesTaxCalculator();

			for (CSVRecord record : records) {
				int quantity = Integer.parseInt(record.get("Quantity").trim());
				String product = record.get("Product").trim();
				BigDecimal price = new BigDecimal(record.get("Price").trim());
				Item item = new Item(quantity, product, price);
				taxCalculator.addItem(item);
			}
			
			taxCalculator.printReceipt(console);

		} catch (FileNotFoundException e) {
			System.out.println("Input file " + input_file + " doesn't exist");
		} catch (IOException io){
			System.out.println("Input file " + input_file + " IOException");
		}finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
