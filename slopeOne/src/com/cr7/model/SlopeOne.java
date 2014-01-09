package com.cr7.model;

import java.util.Iterator;
import java.util.Set;

import com.cr7.dataset.DataSet;

public class SlopeOne implements Model{
	private int userNum;
	private int itemNum;
	private int[][] rateMatrix;
	private Set [] userSets;	//用户倒排
	private Set [] itemSets;
	public void setDataSet(DataSet d){
		userNum = d.getUserNum();
		itemNum = d.getItemNum();
		rateMatrix = d.getRateMatrix();
		userSets = d.getUserSet();
		itemSets = d.getItemSet();
	}
	
	
	public double computeDev(int i,int j){
		double dev = 0;
		int count =0;
		Iterator itr = userSets[i].iterator();
		while(itr.hasNext()){
			int u = (Integer)itr.next();
			if(userSets[j].contains(u)){
				dev+=(rateMatrix[u][j]-rateMatrix[u][i]);
				count++;
			}
		}
		if(count==0) return 0;
		return dev/count;
	}
	
	public double predict(int tarUser,int tarItem){
		int rateNum=0;
		double predictR = 0;
		for(int i=1;i<=this.itemNum;i++){
			if(rateMatrix[tarUser][i]==0) continue;
			double dev = this.computeDev(i, tarItem);
			predictR+=(rateMatrix[tarUser][i]+dev);
			rateNum++;
		}
		//因为每个用户至少有20个评分，所以不存在rateNum==0的情况
		//否则的话 if(rateNum==0) return average;
		predictR/=rateNum;
		return predictR;
	}
}
