package cn.edu.ustc.www;

import java.util.Map;

/**
 * 
 * 
 * evaluate with MAE RMSE
 *
 */

public class Evaluate{
	private double RMSE=0;
	private double MAE=0;
	private double [][] pMatrix;	//predict rating scores of vacant 		
	private Map<Pair,Integer> scoreMap;    //actual rating scores
	public  void init(DataSet d){
		scoreMap = new UIMap(d).getUI();
	}
	public double evaluateMAE(Model m,DataSet d){
		pMatrix = ((SimpleSVDModel)m).getPMatrix();
		init(d);
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			MAE+=Math.abs(scoreMap.get(o)-pMatrix[p.getU()][p.getI()]);
		}
		MAE/=scoreMap.size();
		return MAE;
	}
	public double evaluateRMSE(Model m,DataSet d){
		pMatrix = ((SimpleSVDModel)m).getPMatrix();
		init(d);
		Pair p = new Pair();
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			double a = (scoreMap.get(o)-pMatrix[p.getU()][p.getI()]);
			RMSE+=(a*a);			
		}
		RMSE/=scoreMap.size();
		return Math.sqrt(RMSE);
	}
	public double getRMSE() {
		return RMSE;
	}
	public double getMAE() {
		return MAE;
	} 
}
