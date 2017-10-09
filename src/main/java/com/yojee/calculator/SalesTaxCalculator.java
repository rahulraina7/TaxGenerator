package com.yojee.calculator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.StringJoiner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.yojee.model.Item;
import com.yojee.model.Item.Itemtype;

public class SalesTaxCalculator {
	BigDecimal total_tax = new BigDecimal("0");
	BigDecimal items_total = new BigDecimal("0");

	final BigDecimal salesTax = new BigDecimal("0.10");
	final BigDecimal importDuty = new BigDecimal("0.05");

	ArrayList<Item> receiptItems = new ArrayList<Item>();

	public void addItem(Item item) {
		BigDecimal taxes = new BigDecimal("0");
		BigDecimal sales_tax = new BigDecimal("0");
		BigDecimal import_duties = new BigDecimal("0");
		BigDecimal round = new BigDecimal("0.05");

		if (item.getItemType() == Itemtype.OTHERS) {
			sales_tax = item.getPrice().multiply(new BigDecimal(item.getQuantity())).multiply(salesTax);
			sales_tax = sales_tax.divide(round, RoundingMode.UP);
			sales_tax = sales_tax.setScale(0, RoundingMode.UP).multiply(round);
		}

		if (item.isImported()) {
			import_duties = item.getPrice().multiply(new BigDecimal(item.getQuantity())).multiply(importDuty);
			import_duties = import_duties.divide(round, RoundingMode.UP);
			import_duties = import_duties.setScale(0, RoundingMode.UP).multiply(round);
		}

		if (sales_tax.add(import_duties).compareTo(new BigDecimal("0")) == 1) {
			taxes = sales_tax.add(import_duties);
			updateTotalTax(taxes);
		}
		item.setNetPrice(taxes.add(item.getPrice().multiply(new BigDecimal(item.getQuantity()))));
		items_total = items_total.add(item.getNetPrice());
		receiptItems.add(item);
	}

	public void updateTotalTax(BigDecimal sales_tax) {
		total_tax = total_tax.add(sales_tax);
	}

	public void clearItems() {
		receiptItems.clear();
		total_tax = new BigDecimal("0");
		items_total = new BigDecimal("0");
	}

	public int noOfItems() {
		return receiptItems.size();
	}

	public Item getItem(int index) {
		return receiptItems.get(index);
	}

	public void printReceipt(boolean console) {
		if (console) {
			// Writing to standard out
			for (Item i : receiptItems) {
				StringJoiner joiner = new StringJoiner(", ");
				joiner.add(i.getQuantity() + "");
				joiner.add(i.getName() + "");
				joiner.add(i.getNetPrice() + "");
				System.out.println(joiner.toString());
			}
			System.out.println();
			System.out.println("Sales Taxes: " + total_tax);
			System.out.println("Total: " + items_total);
		} else {
			// Writing to csv
			String outputFile = "output.csv";
			CSVPrinter csvFilePrinter = null;
			CSVFormat csvFileFormat = CSVFormat.EXCEL;
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(outputFile);
				csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

				for (Item i : receiptItems) {
					csvFilePrinter.print(i.getQuantity());
					csvFilePrinter.print(i.getName());
					csvFilePrinter.print(i.getNetPrice());
					csvFilePrinter.println();
				}

				csvFilePrinter.println();
				csvFilePrinter.print("Sales Taxes: " + total_tax);
				csvFilePrinter.println();
				csvFilePrinter.print("Total: " + items_total);

				csvFilePrinter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
