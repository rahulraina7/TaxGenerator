package com.yojee.executor;

import java.math.BigDecimal;
import java.util.Scanner;

import com.yojee.calculator.SalesTaxCalculator;
import com.yojee.model.Item;

public class InteractiveExecutor implements ExecutorService {

	public void execute() {

		Scanner sc = new Scanner(System.in);
		SalesTaxCalculator taxCalculator = new SalesTaxCalculator();

		while (true) {
			String input = null;
			input = sc.nextLine();
			if (input == null || input.trim().equals("")) {
				break;
			}
			if (input.indexOf("Quantity, ") > -1) {
				continue; // Header skip this one
			}
			int quantity = Integer.parseInt(input.split(",")[0].trim());
			String product = input.split(",")[1].trim();
			BigDecimal price = new BigDecimal(input.split(",")[2].trim());
			Item item = new Item(quantity, product, price);
			taxCalculator.addItem(item);
		}
		sc.close();
		taxCalculator.printReceipt(true);
	}

}