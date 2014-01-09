package com.cr7.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import com.cr7.dataSet.DataSet;
import com.cr7.util.MyComparator;
import com.cr7.util.Util;

public class BipartiteNetwork  implements Model{
	int ITEMNUM;
	int USERNUM;
	byte rateMatrix [][];
	float weightMatrix [][];
	int [][] itemDegree;
	int [][] userDegree;
	public BipartiteNetwork(DataSet dataSet){
		ITEMNUM=dataSet.getITEMNUM();
		USERNUM=dataSet.getUSERNUM();
		rateMatrix = dataSet.getRateMatrix();
		weightMatrix = new float[ITEMNUM+1][ITEMNUM+1];
		itemDegree = new int[ITEMNUM+1][2];	
		userDegree = new int[USERNUM+1][2]; //itemDegree[][0] 表示用户喜欢物品的度 itemDegree[][1] 表示用户不喜欢物品的度
		for(int u=1;u<rateMatrix.length;u++){
			for(int i=1;i<rateMatrix[1].length;i++){
				if(rateMatrix[u][i]==1){
					userDegree[u][0]++;
					itemDegree[i][0]++;
				}
				if(rateMatrix[u][i]==-1){
					userDegree[u][1]++;
					itemDegree[i][1]++;
				}
			}
	   }
	}
	
	

	public double predict(int u, int i) {
		return 0;
	}

	/*public int[] getTopNWithMatrix(int u,int n) {
		int [] recomm = new int[n];
		float [] weight = new float[ITEMNUM+1];
	//	weight = Util.multiplyMatrix(weight, weightMatrix);
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==1) weight[i]=1;
			else weight[i]=0;
		}
		TreeMap<Float,Integer> map = new TreeMap<Float,Integer>(new MyComparator());
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==0){
				map.put(weight[i],i);				//把所有未collected的项目添加到推荐候选列表
			}
		}
		Collection<Integer> c = map.values();
		Iterator<Integer> itc = c.iterator();
		int count=0;
		while(itc.hasNext() && count<n){
			recomm[count]=itc.next();
			count++;
		}
		return recomm;
	}*/
	
	public void computeWeight(int u,float [] weight) {
		//根据给定用户计算项目得分
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==1) weight[i]=1;
			else if (rateMatrix[u][i]==-1) weight[i]=-1;
			else weight[i]=0;
		}
//Util.printArray(weight);Util.p("\n***********");
		//从object传递到user的传递物质数组 uWeight[][0]表示正推荐数量 uWeight[][1]表示负推荐数量
		float [][] uWeight = new float[USERNUM+1][2];
		Util.initMatrix(uWeight);
		for(int i=1;i<uWeight.length;i++){
			for(int j=1;j<weight.length;j++){
				//if(itemDegree[j]!=0) uWeight[i]+=(rateMatrix[i][j]*weight[j]/itemDegree[j]);
				if(itemDegree[j][0]!=0) uWeight[i][0]+=((double)rateMatrix[i][j]/itemDegree[j][0]);
				if(itemDegree[j][1]!=0) uWeight[i][1]+=((double)rateMatrix[i][j]/itemDegree[j][1]);
			}
		}
//Util.printArray(uWeight);Util.p("\n***********");
		for(int i=1;i<weight.length;i++){
			float lambda = 0.77f;
			for(int l=1;l<uWeight.length;l++){
				//if(userDegree[l]!=0) weight[i]+=(rateMatrix[l][i]*uWeight[l]/userDegree[l]);
				if(rateMatrix[l][i]==1) weight[i]+=lambda*(uWeight[l][0]/userDegree[l][0]);
				if(rateMatrix[l][i]==-1) weight[i]+=(1-lambda)*(uWeight[l][1]/userDegree[l][1]);
			}
		}
//		Util.printArray(weight);
	}
	
	public void initWeight(int u,float [] weight){
		
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==1) weight[i]=1;
			else weight[i]=0;
		}
	}
	
	public int[] getTopN(int u,int n) {
		int [] recomm = new int[n];
		float [] weight = new float[ITEMNUM+1];
		computeWeight(u,weight);
		TreeMap<Float,Integer> map = new TreeMap<Float,Integer>(new MyComparator());
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==0){
				map.put(weight[i],i);				//把所有未collected的项目添加到推荐候选列表
			}
		}
		Collection<Integer> c = map.values();
		Iterator<Integer> itc = c.iterator();
		int count=0;
		while(itc.hasNext() && count<n){
			recomm[count]=itc.next();
			count++;
		}
		if(u%222==0){
			Util.p(u+" :we recomm you:");
			Util.printArray(recomm);
		}
		return recomm;
	}



	public TreeMap<Integer, Integer> getAll(int u) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

