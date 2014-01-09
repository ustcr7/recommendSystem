package com.cr7.evaluate;

import java.util.Map;
import com.cr7.dataset.DataSet;
import com.cr7.model.Model;

public class Evaluator {
	private double RMSE=0;
	private double MAE=0;
	private Map<Pair,Integer> scoreMap;    //actual rating scores

	public void setDataSet(DataSet d){
		scoreMap = new UIMap(d).getUI();
	}

	public double getMAE(Model m) {
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			MAE+=Math.abs(scoreMap.get(o)-m.predict(p.u, p.i));
			//System.out.println(scoreMap.get(o)-m.predict(p.u, p.i)+" ");
		}
		MAE/=scoreMap.size();
		return MAE;
	} 

	public double getRMSE(Model m) {
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			double a = (scoreMap.get(o)-m.predict(p.u, p.i));
			RMSE+=(a*a);			
		}
		RMSE/=scoreMap.size();
		return Math.sqrt(RMSE);
	}
	
	//专门测试用
	public void getMAEAndRMSE(Model m) {
		
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			double a = (scoreMap.get(o)-m.predict(p.u, p.i));
			RMSE+=(a*a);	
			MAE+=Math.abs(a);
			//System.out.println("MAE;"+MAE+"  RMSE:"+RMSE);
		}
		MAE/=scoreMap.size();
		RMSE/=scoreMap.size();
		System.out.println("MAE;"+MAE+"  RMSE:"+RMSE);
	} 
	//专门测试用
	public void getMAEAndRMSEWithAverage(double ave) {
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			double a = (scoreMap.get(o)-ave);
			RMSE+=(a*a);	
			MAE+=Math.abs(a);
		}
		MAE/=scoreMap.size();
		RMSE/=scoreMap.size();
		System.out.println("MAE;"+MAE+"\nRMSE:"+RMSE);
	} 
}
