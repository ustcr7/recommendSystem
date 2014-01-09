package com.cr7.evaluate;

import java.util.Map;

import com.cr7.dataSet.DataSet;
import com.cr7.model.Model;
import com.cr7.util.Util;

public class Evaluator {
	private Map<Pair,Byte> scoreMap;    //actual rating scores
	public Evaluator(DataSet d){
		this.setDataSet(d);
	}
	public void setDataSet(DataSet d){
		UIMap mp = new UIMap(d);
		scoreMap = mp.getUI();
Util.p(scoreMap.size());
	}
	
	public void getMAE_RMSE(Model m){
		double mae=0;
		double rmse=0;
		int count=0;
		for(Pair p : scoreMap.keySet()){
//			if(count>=4 && count<=16)	Util.p(p.u+" "+p.i+" "+scoreMap.get(p));
			double diff = scoreMap.get(p)-m.predict(p.u, p.i);
			mae+= Math.abs(diff);;
			rmse+=(diff*diff);
			if(count>=280 && count<=320)	Util.p("Êµ¼ÊÆÀ·Ö"+scoreMap.get(p)+" "+count+" diff"+diff+"  mae:"+mae+"  rmse:"+rmse); 
			count++;
		}
		Util.p("mae "+mae);
		Util.p("rmse "+rmse);
		mae/=scoreMap.size();
		rmse=Math.sqrt(rmse/scoreMap.size());
		Util.p("mae "+mae);
		Util.p("rmse "+rmse);
	}
	
}

