package com.cr7.model;

import com.cr7.util.Util;

public class BuildTrust {
	private double [][] trust;
	private int UNUM;	//usernum;
	private int INUM;	//itemnum;
	private int THRESHOLD=1;
	public BuildTrust(byte rateMatrix[][]){
		UNUM = rateMatrix.length-1;
		INUM = rateMatrix[0].length-1;
		trust = new double[UNUM+1][INUM+1];
		Util.init(trust);
		initTrust(rateMatrix);
	}
	public void initTrust(byte rateMatrix[][]){
		for(int i=1;i<=INUM;i++){
			int recomm = 0;		//推荐次数
			int correct = 0;	//正确推荐次数
			for(int p=1;p<=UNUM;p++){
				if(rateMatrix[p][i]==0){
					trust[p][i]=0;
					continue;
				}
				for(int c=1;c<=UNUM;c++){
					if(c==p)	continue;
					if(rateMatrix[c][i]!=0) recomm++;
					if((rateMatrix[p][i]-rateMatrix[c][i])<=THRESHOLD) correct++;
				}
				trust[p][i]=((double)correct/recomm);
			}
		}
		Util.p(trust);
	}
}
