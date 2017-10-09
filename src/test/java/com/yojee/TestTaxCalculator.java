package com.yojee;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yojee.calculator.SalesTaxCalculator;
import com.yojee.model.Item;

public class TestTaxCalculator {

	static SalesTaxCalculator stc;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@BeforeClass
	public static void init() {
		stc = new SalesTaxCalculator();
	}

	@Test
	public void testAddItem() {
		stc.clearItems();
		assertEquals(0, stc.noOfItems());
		stc.addItem(new Item(1, "book", new BigDecimal("12.49")));
		assertEquals(1, stc.noOfItems());
		stc.clearItems();
	}

	@Test
	public void testGenerateRecipt() {
		stc.clearItems();
		stc.addItem(new Item(1, "book", new BigDecimal("12.49")));
		stc.printReceipt(true);
		assertEquals("1, book, 12.49\n\nSales Taxes: 0\nTotal: 12.49\n", outContent.toString());
		stc.clearItems();
	}

	@Test
	public void testSalesTaxOnOthers() {
		stc.clearItems();
		stc.addItem(new Item(1, "Mac Pro", new BigDecimal("2688.00")));
		stc.printReceipt(true);
		assertEquals("1, Mac Pro, 2956.80\n\nSales Taxes: 268.80\nTotal: 2956.80\n", outContent.toString());
		stc.clearItems();
	}

	@Test
	public void testSalesTaxOnMedicine() {
		stc.clearItems();
		stc.addItem(new Item(1, "headache pills", new BigDecimal("12.00")));
		stc.printReceipt(true);
		assertEquals("12.00", stc.getItem(0).getNetPrice().toString());
		stc.clearItems();
	}

	@Test
	public void testSalesTaxOnImportedMedicine() {
		stc.clearItems();
		stc.addItem(new Item(1, "imported headache pills", new BigDecimal("12.00")));
		stc.printReceipt(true);
		assertEquals("12.60", stc.getItem(0).getNetPrice().toString());
		stc.clearItems();
	}

	@Test
	public void testImportDutiesOnFood() {
		stc.clearItems();
		stc.addItem(new Item(1, "imported chocolate box", new BigDecimal("100.00")));
		stc.printReceipt(true);
		assertEquals("105.00", stc.getItem(0).getNetPrice().toString());
		stc.clearItems();
	}

	@Test
	public void testImportAndSalesDutiesOnOthers() {
		stc.clearItems();
		stc.addItem(new Item(1, "imported apple iphone X", new BigDecimal("999.00")));
		stc.printReceipt(true);
		assertEquals("1148.85", stc.getItem(0).getNetPrice().toString());
		stc.clearItems();
	}

	@Test
	public void testClearItems() {
		stc.clearItems();
		stc.addItem(new Item(1, "imported apple iphone X", new BigDecimal("999.00")));
		stc.addItem(new Item(1, "imported apple iphone 7", new BigDecimal("999.00")));
		assertEquals(2, stc.noOfItems());
		stc.clearItems();
		assertEquals(0, stc.noOfItems());
	}

	@After
	public void cleanUpStreams() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

}
