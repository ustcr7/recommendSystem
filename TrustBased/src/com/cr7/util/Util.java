package com.cr7.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Util {
	private static Connection conn ;
	
	public static void save(int u,int v,float trust){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection
							("jdbc:mysql://localhost/epinions","root","910125");
			String sql = "insert into t_trust values("+u+","+v+","+trust+")";
			Statement st = conn.createStatement();
			st.execute(sql);
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	static long start;
	public static void mark(){
		start=System.currentTimeMillis();
	}
	public static void mark(String str){
		System.out.println("·½·¨"+str+" "+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
	}
	public static void init(double [][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				matrix[i][j]=0;
			}
		}
	}
	
	public static BufferedReader getBufferedReader(String file){
		BufferedReader bf = null;
		try{ 
			bf = new BufferedReader(new FileReader(file));
		}catch(Exception e){}
		return bf;
	}
	public static void writeIntoTxt(String result,String filename){	
		try{
			FileWriter writer1 = new FileWriter(filename,true);
			writer1.write(result);
			writer1.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void p(double [] d){
		for(int i=0;i<d.length;i++){
			System.out.print(d[i]+"  ");
			if(i%200==0)	System.out.println();
		}
	}
	public static void p(Object o){
		System.out.println(o);
	}
}
