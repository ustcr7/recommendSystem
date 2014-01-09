package com.cr7.evaluate;

import java.util.HashMap;
import java.util.Map;

import com.cr7.dataset.DataSet;

public class UIMap {
	private Map<Pair,Integer> ui;
	public UIMap(DataSet d){
		ui = new HashMap<Pair,Integer>();
		int [][] m = d.getRateMatrix();
		for(int i=1;i<m.length;i++){
			for(int j=1;j<m[0].length;j++){
				if(m[i][j]!=0){
					Pair p = new Pair(i,j);
					ui.put(p, m[i][j]);
				}
			}
		}
	}
	public Map<Pair, Integer> getUI() { 
		return ui;
	}
	
	
	
	
}
