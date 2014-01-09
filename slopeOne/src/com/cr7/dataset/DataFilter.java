package com.cr7.dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class DataFilter {

	public static void main(String[] args) {
		
		BufferedReader bf;
		String scoreFile = "E:\\数据挖掘数据\\ml-1000k\\ml-1m\\ratings.dat";
		int [] userRate = new int[6041];
		try {
			bf = new BufferedReader(new FileReader(scoreFile));
			String line = "";
			while((line=bf.readLine())!=null){
				int i = Integer.parseInt(line.split("::")[0]);
				userRate[i]++;
			}
		}catch (Exception e){		
		}
		
		try {
			FileWriter writer1 = new FileWriter("E:\\数据挖掘数据\\ml-1000k\\ml-1m\\ua.base");
			FileWriter writer2 = new FileWriter("E:\\数据挖掘数据\\ml-1000k\\ml-1m\\ua.train");
			bf = new BufferedReader(new FileReader(scoreFile));
			for(int u=1;u<=6040;u++){
				String line = "";
				String out = "";
				int train=(int)(userRate[u]*0.8);
//				int test=userRate[u]-train;
				
				for(int j=0;j<userRate[u];j++){
					out="";
					line=bf.readLine();
					String [] array = line.split("::");
					out+=array[0]+"\t"+array[1]+"\t"+array[2]+"\r\n";
					if(j<train){
						writer1.write(out);
					}else{
						writer2.write(out);
					}
				}
				
			}
		}catch (Exception e){		
		}
		/*
		for(int i=1;i<userRate.length;i++){
			System.out.println(i+" "+userRate[i]);
		}*/
	}

}
