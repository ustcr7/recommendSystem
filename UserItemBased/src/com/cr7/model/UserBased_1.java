package com.cr7.model;

import java.util.Iterator;
import java.util.TreeMap;

import com.cr7.dataSet.DataSet;
import com.cr7.util.Util;

public class UserBased_1 implements Model {
	private int USERNUM;
	private int ITEMNUM;
	private double ave[];	//用户的评分均值
	private byte rateMatrix [][];	//用户评分矩阵
	private double simMatrix [][];	//用户相似度矩阵
	private int NEIGHBOURNUM=30;	//邻居数目
	private int neighbour [][];
	public UserBased_1(DataSet d,int l){
		NEIGHBOURNUM = l;
		Util.p("");
		initWithDataSet(d);
//		Util.p("initWithDataSet");
		computeSimilarity();
//		Util.p("computeSimilarity()");
		computeNeighbour();
		
		for(int u=1;u<=USERNUM;u++ ){
			for(int j=0;j<NEIGHBOURNUM;j++){
				Util.writeIntoTxt(u+" "+neighbour[u][j]+"\t", "E:\\数据挖掘数据\\epinions\\filterData\\neighbour");
			}
			Util.writeIntoTxt("\r\n", "E:\\数据挖掘数据\\epinions\\filterData\\neighbour");
		}
//		Util.p(neighbour);
//		Util.p("computeNeighbour()");
	}
	
	private void initWithDataSet(DataSet d){
		USERNUM=d.getUSERNUM();
		ITEMNUM=d.getITEMNUM();
		rateMatrix=d.getRateMatrix();
		ave = d.getAverage();
		Util.p("\n**************");
		Util.p(ave);
	}
	
	private void computeSimilarity(){					//离线计算
		double e=0;
		int T=30;
		simMatrix = new double [USERNUM+1][USERNUM+1];
		Util.init(simMatrix);
		for(int u=1;u<=USERNUM;u++){
			for(int v=u+1;v<=USERNUM;v++){
				double nominator=0;
				double denomiU=0;
				double denomiV=0;
				int count = 0;
				for(int i=1;i<=ITEMNUM;i++){
					double ru=rateMatrix[u][i];
					double rv =rateMatrix[v][i];
					if(ru!=0 && rv!=0){		//u,v对i的评分均不为0
						count++;
						double diffu =(ru-ave[u]);
						double diffv = (rv-ave[v]);
						nominator+=(diffu*diffv);
						denomiU+=(diffu*diffu);
						denomiV+=(diffv*diffv);
					}
				}
				if(Math.abs(denomiU)<=0.0001 || Math.abs(denomiV)<=0.0001){
					simMatrix[u][v]=0;
				}else{
					if(count>=T) e=1;
					else e=(double)count/T;
					simMatrix[u][v]=e*(nominator/(Math.sqrt(denomiU*denomiV)));
				}
				simMatrix[v][u]=simMatrix[u][v];
			}
		}
		
		for(int i=1;i<USERNUM;i++){
			System.out.print(i+" "+simMatrix[2][i]+";\t");
		}
	}
	
	public void computeNeighbour(){						//离线计算
		neighbour = new int [USERNUM+1][NEIGHBOURNUM];
		for(int u=1;u<=USERNUM;u++){
			neighbour[u]=getNeighbours(u,NEIGHBOURNUM);
		}
		Util.p("user 1的邻居集:");
		Util.p(neighbour[1]);
	}
	public int[] getTopN(int u, int n) {
		int [] recomm = new int[n];
		TreeMap<Double,Integer> map = new TreeMap<Double,Integer>();
		for(int i=1;i<=ITEMNUM;i++){
			if(rateMatrix[u][i]==0) map.put(-1*predict(u,i), i);
//			Util.p(i+":"+predict(u,i)+"; ");
		}
		Iterator<Integer> itr = map.values().iterator();
		for(int i=0;i<n & itr.hasNext();i++){
			recomm[i]=itr.next();
		}
		Util.p(recomm);
		return recomm;
	}

	public double predict(int u, int i) {	//l是选取的邻居数目
		double predict = 0;
		double nominator = 0;
		double denominator = 0;
		int count =0;
		for(int k=0;k<NEIGHBOURNUM;k++){
			int v = neighbour[u][k];
			if(v==0)	break;		//由于数据及其稀疏，可能部分用户邻居数达不到30（则值为0，）
			double simuv = simMatrix[u][v];
			if(simuv==0) Util.p(u+" "+i);
			if(rateMatrix[v][i]!=0){
				nominator+=simuv*(rateMatrix[v][i]-ave[v]);
				denominator+=Math.abs(simuv);
				count++;
			}
		}
		if(count==0){	//u的N个最近邻居都没有对i进行评分，所以只能用u的平均分预测了。
//			Util.p(u+"的N个最近邻居都没有对"+i+"进行评分，所以只能用平均分预测了");
			predict=ave[u];
		}else{
//			Util.p(u+" "+i+" "+nominator/denominator);
			if(Math.abs(denominator)<=0.0001) predict = ave[u];
			else   				predict=(ave[u]+nominator/denominator);
		}
		return predict;
	}
	
	private int []  getNeighbours(int u,int l){
		int [] neighbours = new int[l];
		TreeMap<Double,Integer> map = new TreeMap<Double,Integer>();
		for(int v=1;v<=USERNUM;v++){
			if(simMatrix[u][v]!=0) map.put(-1*simMatrix[u][v],v);
		}
		Iterator<Integer> itr = map.values().iterator();
		for(int i=0;i<neighbours.length & itr.hasNext();i++){
			neighbours[i]=itr.next();
		}
		return neighbours;
	}

}

