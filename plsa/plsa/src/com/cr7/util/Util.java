package com.cr7.util;

import java.io.FileWriter;
import java.util.Random;

public class Util {
	
	public static boolean trueWithProbability(int n){
		int rnd = new Random().nextInt(n);
		return (777==rnd);
	}
	
	public static double gaussianProb(byte value,double mean,double deviation){
		double prob = 0;

		return prob;
	}
	
	 /**
	  * 正态分布函数近似值，使用6项近似余函数
	  */
	 private static double Fi_erf_6(double x){
		 double a=Math.abs(x);
		 return 0.5*(1+erf_6(a/Math.sqrt(2)));
	 }
	 /**
	  * 正态分布函数六项级数近似余函数
	  */
	 private static double erf_6(double x){
		double a[]={0.070523084,0.0422820123,0.0092705272,0.0001520143,0.0002765672,0.0000430638};
		double t=0;
		for(int i=0;i<6;i++){
			t=t+a[i]*Math.pow(x, i+1);
		}
		return 1-Math.pow(1+t, -16);
	 }
	 /**
	  * 正态分布函数值
	  */
	 public static double Ni(double x){
		 return x==0?0.5:(x>0?Fi_erf_6(x):1-Fi_erf_6(x));
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
	public static void writeIntoTxt(double [][] matrix,String filename){
		try{
			FileWriter writer1 = new FileWriter(filename,true);
			for(int i=0;i<matrix.length;i++){
				for(int j=0;j<matrix[0].length;j++){
					writer1.write(matrix[i][j]+"\t");
				}
				writer1.write("\r\n");
			}
			writer1.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void writeIntoTxt(double [] matrix,String filename){
		try{
			FileWriter writer1 = new FileWriter(filename,true);
			for(int i=0;i<matrix.length;i++){
					writer1.write(matrix[i]+"\t");
			}
			writer1.write("\r\n");
			writer1.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static long start;
	public static void setStart(long start) {
		Util.start = start;
	}
	public static void mark(){
		System.out.println(System.currentTimeMillis()-start);
		start=System.currentTimeMillis();
	}
	public static void mark(String str){
		System.out.println("方法"+str+" "+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
	}
	public static boolean exists(int [] arr,int n){
		boolean b = false;
		for(int i=0;i<arr.length;i++){
			if(n==arr[i]) {
				b = true;
				break;
			}
		}
		return b;
	}
	
	public static void initArray(int [] a){
		for(int i=0;i<a.length;i++){
			a[i] = 0;
		}
	}
	
	public static void initMatrix(long [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	
	public static void initMatrix(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	
	public static void initMatrix(long [] a){
		for(int i=0;i<a.length;i++){
			a[i] = 0;
		}
	}
	
	public static void init(int [] a){
		for(int i=0;i<a.length;i++){
				a[i]=0;
		}
	}
	public static void init(double [] a){
		for(int i=0;i<a.length;i++){
				a[i]=0;
		}
	}
	public static void initMatrix(int [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	public static void initMatrix(byte [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	public static void init(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	public static void p(double [] a){
		for(int i=0;i<a.length;i++){
				System.out.print(a[i]+" ");
				if(i%100==0)System.out.print("\n");
		}
	}
	public static void p(Object o){
		System.out.println(o);
	}
}
