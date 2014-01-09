package com.cr7.dataSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cr7.util.Util;

public class CopyDataSet {
	  int USERNUM=943;		//6040
	 int ITEMNUM=1682;	//3952
	  int RATINGNUM;
	 double TOTALAVERAGE;
	 public int scoreMatrix[][];
	 Set [] userSets = new Set[this.ITEMNUM+1];
	 Set<Integer> [] itemSets = new Set[this.USERNUM+1];
	/*String userFile="";
	String itemFile="";*/
	 String scoreFile="";
	 public void init(){
		 for(int i=1;i<userSets.length;i++){
			 userSets[i] = new HashSet<Integer>();
		 }
		 for(int i=1;i<itemSets.length;i++){
			 itemSets[i] = new HashSet<Integer>();
		 }
	 }
	 public CopyDataSet(){
		 scoreFile = "E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.base";
		 this.init();
	 }
	public CopyDataSet(String file){
		scoreFile = file;
		this.init();
	}
	public  int [][] getRateMatrix(){
		int scoreMatrix [][] = new int[USERNUM+1][ITEMNUM+1];
		Util.initMatrix(scoreMatrix);
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(scoreFile));
			String line = "";
			int u=0;	//user
			int i=0;	//item
			int s=0;	//score
			while((line=bf.readLine())!=null){
				Pattern p = Pattern.compile("[\\w]+");
				Matcher m = p.matcher(line);
				if(m.find()) u=Integer.parseInt(m.group());
				if(m.find()) i=Integer.parseInt(m.group());
				if(m.find()) s=Integer.parseInt(m.group());
				scoreMatrix[u][i]=s;
				userSets[i].add(u);
				itemSets[u].add(i);
				RATINGNUM++;
				TOTALAVERAGE+=s;				
			}
			TOTALAVERAGE/=RATINGNUM;
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		this.scoreMatrix=scoreMatrix;
		return scoreMatrix;
	}
	
	public double [] getAverageRate1(){				//获取项目平均评分
		double [] ave = new double[this.ITEMNUM+1];
		for(int i=1;i<ave.length;i++){
			int count=0;
			for(int u=1;u<this.USERNUM+1;u++){
				int r = scoreMatrix[u][i];
				if(r!=0){
					ave[i]+=r;
					count++;
				}
			}
			if(count==0) ave[i]=0;
			else		ave[i]/=count;
		}
		return ave;
	}
	
	public double [] getAverageRate2(){				//获取用户平均评分
		double [] ave = new double[this.USERNUM+1];
		for(int u=1;u<ave.length;u++){
			int count=0;
			for(int i=1;i<this.ITEMNUM+1;i++){
				int r = scoreMatrix[u][i];
				if(r!=0){
					ave[u]+=r;
					count++;
				}
			}
			if(count==0) ave[u]=0;
			else		ave[u]/=count;
		}
		return ave;
	}
	
	public Set[] getUserSet(){
		return this.userSets;
	}
	public Set[] getItemSet(){
		return this.itemSets;
	}
	
	public  int getUserNum(){ 
		return USERNUM;
	}
	public  int getItemNum(){
		return ITEMNUM;
	}
	public  double getTotalAverage(){
		this.getRateMatrix();
		return TOTALAVERAGE;
	}
}
