package com.cloudapp.lib;

import java.util.Scanner;
 
 public class BiosID {
	 
 	public static String getBiosID () {
 		try {
	        Process process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialnumber" });
	        process.getOutputStream().close();
	        Scanner sc = new Scanner(process.getInputStream());
	        // String property = sc.next();
	        String serial = new String(sc.next());
	        sc.close();
	        return serial;
 		} catch (Exception e) {
 			return null;
 		}
	}
 	
}
