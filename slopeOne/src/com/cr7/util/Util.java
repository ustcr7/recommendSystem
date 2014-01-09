package com.cr7.util;

public class Util {
	public static void initMatrix(long [][] a){
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
	
	public static void initMatrix(int [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
}
