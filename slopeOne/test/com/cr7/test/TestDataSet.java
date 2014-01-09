package com.cr7.test;

import java.util.Iterator;

import com.cr7.dataset.DataSet;

public class TestDataSet {
	
	public static void main(String[] args) {
		/*double a=3.0;
		int b=2;
		System.out.println(a/b);*/
		
		DataSet d = new DataSet();
		System.out.println(d.getTotalAverage());
		/*	int mm[][] = d.getRateMatrix();
		for(int i=1;i<d.getUserNum();i++){
			if(mm[i][2]!=0 & mm[i][6]!=0)
				System.out.println(i);
		}*/
		/*Iterator itr = d.getUserSet()[166].iterator();
		
		while(itr.hasNext()){
			System.out.println(itr.next());
		}*/

		//System.out.println(d.getUserSet()[271].contains(801));
	}

}
