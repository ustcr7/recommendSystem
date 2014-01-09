package cn.edu.ustc.www;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestDataSet {
	DataSet ds;
	@BeforeClass
	public void before(){
		ds = new DataSet("E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.base");
	}
	@Test
	public void testGetUserNum(){
		assertThat(ds.getUserNum(),is(943));
	}
	@Test
	public void testGetItemNum(){
		assertThat(ds.getItemNum(),is(1682));
	}
	
	public  void testTOTALAVERAGE(){
		System.out.println(ds.getTotalAverage());
	}
	
	
	public void testGetScoreMatrix(){
		int [][] m = ds.getScoreMatrix();
		int u=943;
		for(int i=1;i<=ds.getItemNum();i++){
			if (m[u][i]!=0) System.out.println(u+"  "+i+"  "+m[u][i]);
		}
	}
	
	public static void testRegex(){
		String line = "1|24|M|technician|85711"; 
		Pattern p = Pattern.compile("[\\w]+");
		Matcher m = p.matcher(line);
		m.find();
		System.out.println(m.group());
		m.find();
		System.out.println(m.group());
		m.find();
		System.out.println(m.group());
		m.find();
		System.out.println(m.group());
		while(m.find()){
			System.out.println(m.group());
		} 
		String line2 = "1	1	5	874965758";
		Pattern p2 = Pattern.compile("[\\w]+");
		Matcher m2 = p2.matcher(line2);
		while(m2.find()){
			System.out.println(m2.group());
		} 
	}
	
	public static void main(String args[]){
		//testRegex();
		//testGetScoreMatrix();
		//testTOTALAVERAGE();
	}
}

