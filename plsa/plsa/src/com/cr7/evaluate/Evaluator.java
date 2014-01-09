package com.cr7.evaluate;

import java.util.Map;
import com.cr7.dataSet.DataSet;
import com.cr7.model.Plsa;

public class Evaluator {
	private double RMSE=0;
	private double MAE=0;
	private Map<Pair,Byte> scoreMap;    //actual rating scores
	public void setDataSet(DataSet d){
		UIMap mp = new UIMap(d);
		scoreMap = mp.getUI();
	}

	//专门测试用
	public void getMAEAndRMSE(Plsa plsa) {
		Pair p = new Pair();
		System.out.println("总共有："+scoreMap.size());
		for(Object o : scoreMap.keySet()){
			p = (Pair)o;
			scoreMap.get(o);
			double pre = 0;
			for(int z=1;z<=plsa.CATEGORY;z++){
				pre+=(plsa.mean[p.i][z]*plsa.prob_uz[p.u][z]);
			}
			double a = (scoreMap.get(o)-pre);
			RMSE+=(a*a);	
			MAE+=Math.abs(a);
		}
		MAE/=scoreMap.size();
		RMSE/=scoreMap.size();
		
		System.out.println("MAE;"+MAE+"\t"+"  RMSE:"+RMSE);
	} 
}
