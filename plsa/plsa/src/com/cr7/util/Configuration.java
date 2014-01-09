package com.cr7.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class Configuration {
	public static int usernum;
	public static int itemnum;
	static{
		try{
			BufferedReader buff = new BufferedReader(new FileReader("config.txt"));
			usernum = Integer.parseInt((buff.readLine().split("="))[1]);
			itemnum = Integer.parseInt((buff.readLine().split("="))[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
