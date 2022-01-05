 package com.cloudapp.lib;

import java.util.HashMap;
import java.util.Map;
 
public class StringHashmap {

	// 一组k-v用"$"分割，k与v用":"分割
	public static Map<String, String> string2hashmap(String conf) {
		Map<String, String> result = new HashMap<String, String>();
		if(conf != null && conf != "") {
			for (String kv : conf.split("\\$")){
				String [] tmp = kv.split(":");
				if(tmp.length == 2) {
					String k = tmp[0];
					String v = tmp[1];
					result.put(k, v);
				}
			}
		} 
		return result;
	}
	
	public static String hashmap2string(Map<String, String> conf) {
		String result = "";
		for(String k : conf.keySet()) {
			result += "$" + k + ":" + conf.get(k);
		}
		return result;
	}
 	
}
