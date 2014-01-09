package com.cr7.test;

import com.cr7.dataset.DataSet;
import com.cr7.evaluate.Evaluator;
import com.cr7.model.Model;
import com.cr7.model.WeightedSlopeOne;

public class TestModel {
	public static void main(String args[]){
		
		long start = System.currentTimeMillis();
		DataSet d = new DataSet("E:\\数据挖掘数据\\ml-1000k\\ml-1m\\ua.base");
		double ave = d.getTotalAverage(); 	System.out.println(ave);
		//Model m = new SlopeOne();
		Model m = new WeightedSlopeOne();
		m.setDataSet(d);
		
		DataSet dt = new DataSet("E:\\数据挖掘数据\\ml-1000k\\ml-1m\\ua.train");
		Evaluator e = new Evaluator();
		e.setDataSet(dt);
		e.getMAEAndRMSE(m);
	//	e.getMAEAndRMSEWithAverage(ave);

		
		
		
		long finish = System.currentTimeMillis();
		
		System.out.println("time"+(double)(finish-start)/60000+"mins");
	}
	
	static void p(Object o){
		System.out.println(o);
	}
}
