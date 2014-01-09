package cn.edu.ustc.www;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
public class TestUtil {
	@Test
	public void testUtil(){
		int [][] a = new int [3][5];
		assertThat(a.length,is(3));
		assertThat(a[0].length,is(5));
	}
	
	@Test
	public void testMultiply(){
		int [] a = {2,1,5};
		int [] b = {3,3,2};
		assertThat(Util.multiply(a, b),is(19.0));
		double [] aa = {0,1.1,-1};
		double [] bb = {3,2,3};
		assertThat(Util.multiply(aa, bb),is(-0.8));
	}
	
	public static void main(String args[]){
		double a[][] = new double [3][7];
		Util.initWithRandom(a);
		Util.print2DMatrix(a);
	}
}
