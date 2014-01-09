package com.cr7.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import com.cr7.dataSet.DataSet;
import com.cr7.util.MyComparator;
import com.cr7.util.Util;

public class BipartiteNetworkWithMatrix  implements Model{
	int ITEMNUM;
	int USERNUM;
	byte rateMatrix [][];
	float weightMatrix [][];
	int [] itemDegree;
	int [] userDegree;
	public BipartiteNetworkWithMatrix(DataSet dataSet){
		ITEMNUM=dataSet.getITEMNUM();
		USERNUM=dataSet.getUSERNUM();
		rateMatrix = dataSet.getRateMatrix();
		weightMatrix = new float[ITEMNUM+1][ITEMNUM+1];
		itemDegree = new int[ITEMNUM+1];
		userDegree = new int[USERNUM+1];
		for(int u=1;u<rateMatrix.length;u++){
			for(int i=1;i<rateMatrix[1].length;i++){
				if(rateMatrix[u][i]!=0){
					userDegree[u]++;
					itemDegree[i]++;
				}
			}
	   }
		this.computeWeightMatrix();
	}
	
	


	public void computeWeightMatrix(){
		for(int i=1;i<=ITEMNUM;i++){
			for(int j=1;j<=ITEMNUM;j++){
				if(itemDegree[j]==0)
					weightMatrix[i][i]=0;
				else{
					for(int u=1;u<=USERNUM;u++){
						if(userDegree[u]!=0) weightMatrix[i][j]+=(rateMatrix[u][i]*rateMatrix[u][j]/(float)userDegree[u]);
					}
					weightMatrix[i][j]/=itemDegree[j];
				}
			}
			
		}
	}
	
	public int[] getTopN(int u,int n) {
//		Util.p("getTopN");
		int [] recomm = new int[n];
		float [] weight = new float[ITEMNUM+1];
		float [] phantom= new float[ITEMNUM+1];   //用影子向量，保证迭代中使用原始数据
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==1) {weight[i]=1;phantom[i]=1;}
			else {weight[i]=0;phantom[i]=0;}
		}
		for(int i=1;i<weight.length;i++){
			for(int j=1;j<weightMatrix[1].length;j++){
				weight[i]+=(weightMatrix[i][j]*phantom[j]);		
			}
		}
		TreeMap<Float,Integer> map = new TreeMap<Float,Integer>(new MyComparator());
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==0){
				map.put(weight[i],i);				//把所有未collected的项目添加到推荐候选列表
			}
		}
		Collection<Integer> c = map.values();		//按照keys的大小顺序存value到collection
		Iterator<Integer> itc = c.iterator();		
		int count=1;
		while(itc.hasNext() && count<n){
			recomm[count]=itc.next();
			count++;
		}
//  Util.printArray(recomm);
		return recomm;
	}

	//获取用户所有未评分项目，按照与测评分高低排序
	public TreeMap<Integer,Integer> getAll(int u) {
//		Util.p("\r\ngetAll");
		TreeMap<Integer,Integer> recomm = new TreeMap<Integer,Integer>();	//<id,排名>
		float [] weight = new float[ITEMNUM+1];
		float [] phantom= new float[ITEMNUM+1];   //用影子向量，保证迭代中使用原始数据
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==1) {weight[i]=1;phantom[i]=1;}
			else {weight[i]=0;phantom[i]=0;}
		}
		for(int i=1;i<weight.length;i++){
			for(int j=1;j<weightMatrix[1].length;j++){
				weight[i]+=(weightMatrix[i][j]*phantom[j]);		
			}
		}
		TreeMap<Float,Integer> map = new TreeMap<Float,Integer>(new MyComparator());
		for(int i=1;i<weight.length;i++){
			if(rateMatrix[u][i]==0){
				map.put(weight[i],i);				//把所有未collected的项目添加到推荐候选列表
			}
		}
		Collection<Integer> c = map.values();
		Iterator<Integer> itc = c.iterator();
		int location=1;
		while(itc.hasNext()){
			int item = itc.next();
			recomm.put(item,location);
			location++;
//			Util.p(location+" "+item+" :"+recomm.get(item));
		}
		
		return recomm;
	}
	

	

	public void computeWeight(int u, float[] weight) {
		
	}

	public double predict(int u, int i) {
		return 0;
	}
	
}
