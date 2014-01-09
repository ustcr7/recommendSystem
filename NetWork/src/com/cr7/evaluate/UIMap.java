package com.cr7.evaluate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cr7.dataSet.DataSet;


public class UIMap {
	private Map<Pair,Byte> ui;
	public UIMap(DataSet d){
		ui = new HashMap<Pair,Byte>();
//		byte [][] m = d.getRateMatrix();
		byte [][] m = d.getRateMatrix();
//		Util.p(m.);
		for(int i=1;i<m.length;i++){
			for(int j=1;j<m[0].length;j++){
				if(m[i][j]!=0){
					Pair p = new Pair(i,j);
					ui.put(p, m[i][j]);
				}
			}
		}
	}
	public Map<Pair, Byte> getUI() { 
		return ui;
	}
	
	public boolean containsItem(int item){
		boolean b = false;
		Iterator<Pair> itr = ui.keySet().iterator();
		while(itr.hasNext()){
			if(((Pair)itr.next()).i==item) b = true;
		}
		return b;
	}
	
	
	
}
