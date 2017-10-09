package com.yojee.runner;

import com.yojee.executor.FileExecutor;
import com.yojee.executor.InteractiveExecutor;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		if (args.length > 0 && args.length == 2) {
			new FileExecutor(args[0].trim(), args[1] != null ? true : false).execute();
		} else if (args.length > 0 && args.length == 1) {
			new FileExecutor(args[0].trim(), false).execute();
		} else {
			new InteractiveExecutor().execute();
		}
	}
}
