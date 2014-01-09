package com.cr7.dataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cr7.util.Util;

public class MovieLensDataSet {
	int ITEMNUM=1682;	//直接给出，也可从文件读取但是比较麻烦
	int USERNUM=943;
	
	private int userDegree[];
	private int itemDegree[];
	public MovieLensDataSet(String file){
		readRecord(file);
	}
	//读取评分矩阵
	private void readRecord(String rateFile){
		userDegree = new int[USERNUM+1];
		itemDegree = new int[ITEMNUM+1];
		Util.init(userDegree);Util.init(itemDegree);
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(rateFile));
			String line = "";
			int u=0;	//user
			int i=0;	//item
			byte s=0;	//score
			Util.mark("");
			while((line=bf.readLine())!=null){
				Pattern p = Pattern.compile("[\\w]+");
				Matcher m = p.matcher(line);
				if(m.find()) u=Integer.parseInt(m.group());
				if(m.find()) i=Integer.parseInt(m.group());
				if(m.find()) s=Byte.parseByte(m.group());
				userDegree[u]++;
				itemDegree[i]++;
			}
			Util.mark("读数据");
			for(u=1;u<=USERNUM;u++){
				Util.writeIntoTxt(u+"\t"+userDegree[u]+"\r\n", "E:\\数据挖掘数据\\ml-100k\\数据描述——用户度.txt");
			}
			for(i=1;i<=ITEMNUM;i++){
				Util.writeIntoTxt(i+"\t"+itemDegree[i]+"\r\n", "E:\\数据挖掘数据\\ml-100k\\数据描述——项目度.txt");
			}
			Util.mark("写数据");
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	
}
