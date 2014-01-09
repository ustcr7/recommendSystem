package cn.edu.ustc.www;

import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSet {
	  int USERNUM=943;
	 int ITEMNUM=1682;
	  int RATINGNUM;
	 double TOTALAVERAGE;
	/*String userFile="";
	String itemFile="";*/
	 String scoreFile="";
	public DataSet(String file){
		scoreFile = file;
	}
	public  int [][] getScoreMatrix(){
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
				RATINGNUM++;
				TOTALAVERAGE+=s;				
			}
			TOTALAVERAGE/=RATINGNUM;
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		return scoreMatrix;
	}
	public  int getUserNum(){ 
		return USERNUM;
	}
	public  int getItemNum(){
		return ITEMNUM;
	}
	public  double getTotalAverage(){
		this.getScoreMatrix();
		return TOTALAVERAGE;
	}
}
