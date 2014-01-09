package cn.edu.ustc.www;

import java.util.Random;

public class Util {
	/**init UFMatrix IFMatrix **/
	static Random r = new Random();
	public static void initMatrix(double [][] a,double range){
		
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=r.nextDouble()*0.07+range;
				//a[i][j] = r.nextDouble()*0.1/Math.sqrt(50);
			}
		}
	}
	
	
	
	public static void initWithRandom(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=Math.random()*0.01+0.2;
			}
		}
	}
	public static double multiply(int [][] a,int [][] b){
		return 0;
	}
	public static double multiply(int [] a,int [] b){
		double result=0;
		if (a.length!=b.length) return -1;
		for(int i=0;i<a.length;i++){
			result+=(a[i]*b[i]);
		}
		return result;
	}
	public static double multiply(double [][] a,double [][] b){
		return 0;
	}
	public static double multiply(double [] a,double [] b){
		double result=0;
		if (a.length!=b.length) return -1;
		for(int i=0;i<a.length;i++){
			result+=(a[i]*b[i]);
		}
		return result;
	}
	/*public static double matrixMultiply(int [][] a,int [][] b){
		return 0;
	}
	public static double VectorMultiply(int [] a,int [] b){
		return 0;
	}*/
	public static void initMatrix(int [] a){
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
	public static void initMatrix(double [] a){
		for(int i=0;i<a.length;i++){
			a[i] = 0;
		}
	}
	public static void initMatrix(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				a[i][j]=0;
			}
		}
	}
	public static void print2DMatrix(double [][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				System.out.print(a[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
}
