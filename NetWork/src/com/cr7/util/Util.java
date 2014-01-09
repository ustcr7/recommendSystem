package com.cr7.util;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Vector;

public class Util {
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
	
	public static void writeIntoTxt(String result,String filename){
		try{
			FileWriter writer1 = new FileWriter(filename,true);
			writer1.write(result);
			writer1.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static int [] copyVectorToArray(Vector<Integer> v){
		int [] list = new int[v.size()];
		for(int i=0;i<v.size();i++){
			list[i]=v.get(i);
		}
		return list;
	}
	
	public static void sortArray(int [] array){
		Arrays.sort(array);
	}
	
	public static void sortArray(float [] array){
		Arrays.sort(array);
	}
	
	
	static long start;
	public static void mark(){
		//System.out.println(System.currentTimeMillis()-start);
		start=System.currentTimeMillis();
	}
	public static void mark(String str){
		System.out.println("方法"+str+" "+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
	}
	
	//初始化数组
	public static void initMatrix(float [][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				matrix[i][j]=0;
			}
		}
	}
	public static void initMatrix(byte [][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				matrix[i][j]=0;
			}
		}
	}public static void initMatrix(float [] matrix){
		for(int i=0;i<matrix.length;i++){
				matrix[i]=0;
		}
	}
	//打印数组
	public static void printArray(int [] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+"\t");
			if(i%100==0) System.out.print("\n");
		}
	}
	public static void printArray(float [] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
			if(i%100==0) System.out.print("\n");
		}
	}
	public static void printArray(float [][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(byte [][] matrix){
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				System.out.print(matrix[i][j]+"\t");
			}
			System.out.println();
		}
	}
	public static void p(Object o){
		System.out.println(o);
	}
}
