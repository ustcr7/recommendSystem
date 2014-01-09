package com.cr7.model;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.cr7.dataSet.DataSet;
import com.cr7.evaluate.Evaluator;
import com.cr7.util.Util;

public class Plsa {
	private int USERNUM;
	private int ITEMNUM;
	private byte rateMatrix [][];	//用户评分矩阵
	 Set<Integer> [] userSets;	//存放对某个item评分的所有用户
	 Set<Integer> [] itemSets;
	public int CATEGORY;
	public double mean[][];	//待学习参数--均值
	private double deviation[][];//待学习参数--方差
	public double prob_uz [][];//待学习参数--p(z|u)
	private double postProb [][][];//待学习参数--z的后验概率p(z|u,v,y)
	private int MAX_LOOP=20;
	public static final double NEARZERO=0.00000000001;
	public Plsa(DataSet d,int category){
		initWithDataSet(d);
		this.CATEGORY = category;
		mean = new double [ITEMNUM+1][CATEGORY+1];
		deviation = new double [ITEMNUM+1][CATEGORY+1];
		prob_uz = new double[USERNUM+1][CATEGORY+1];
		postProb =new double[USERNUM+1][ITEMNUM+1][CATEGORY+1];
		initParameter();
	}
	
	private void initWithDataSet(DataSet d){
		USERNUM=d.getUSERNUM();
		ITEMNUM=d.getITEMNUM();
		rateMatrix=d.getRateMatrix();
		userSets = d.getUserSets();
		itemSets = d.getItemSets();
		
		Util.p(userSets.length);
	}
	private void initParameter(){
		//均值参数设为3分上下浮动1分。
		for(int i=1;i<=ITEMNUM;i++){
			for(int z=1;z<=CATEGORY;z++){
				double ran = 2*(new Random().nextDouble());
				mean[i][z]=(4-ran);
			}
		}
		//方差参数设为1.5上下浮动0.5
		for(int i=1;i<=ITEMNUM;i++){
			for(int z=1;z<=CATEGORY;z++){
				deviation[i][z]=(2-(new Random().nextDouble()));
			}
		}
		//参数--p(z|u)设置为1/category
		for(int u=1;u<=USERNUM;u++){
			for(int z=1;z<=CATEGORY;z++){
				prob_uz[u][z]=1/(double)CATEGORY;
			}
		}
//		Util.writeIntoTxt(prob_uz,"E:\\puz.txt");
	}
	
	public void trainModel(){
		int count=0;
	Util.mark();
		while(count<MAX_LOOP){
			computePostProbability();
//	Util.mark("computePostProbability");
			computeParameter();
//	Util.mark("computeParameter");
			computeLostFunction();
//	Util.mark("computeLostFunction");
			count++;
			if(count>=28){
				for(int u=678;u<=700;u++){
					for(int i=1;i<=ITEMNUM;i++){
						Util.writeIntoTxt(postProb[u][i],"E:\\postProb"+count+".txt");
					}
				}
				Util.writeIntoTxt(mean,"E:\\mean"+count+".txt");
				Util.writeIntoTxt(deviation,"E:\\devia"+count+".txt");
				Util.writeIntoTxt(prob_uz,"E:\\puz"+count+".txt");
			}
	Util.mark("迭代时间");
		}
	}
	
	private void computePostProbability(){
		for(int u=1;u<=USERNUM;u++){
			Iterator<Integer> items = itemSets[u].iterator();
			while(items.hasNext()){
				int i = items.next();
				double total = 0;
				double pv [] = new double[CATEGORY+1];
				for(int z=1;z<=CATEGORY;z++){
					double p = (rateMatrix[u][i]-mean[i][z])/deviation[i][z];
					if(Double.isNaN(p)){
							Util.p(deviation[i][z]);
					}
					pv[z] = prob_uz[u][z]*Util.Ni(p);
					total+=(pv[z]);
				}
				for(int z=1;z<=CATEGORY;z++){
					postProb[u][i][z]=(pv[z]/total);
				}
			}
		}
		
	}
	private void computeParameter(){
		/**
		 * 
		 * 注意可能存在useset或者itemset为0的情况
		 */
		//计算参数p(z|u)
		for(int u=1;u<=USERNUM;u++){
			if(itemSets[u].size()==0) continue;	//如果u没有选择任何用户那么他的p(z|u)只能为初始的均匀分布
			Iterator<Integer> items = itemSets[u].iterator();
			double p[]  = new double[CATEGORY+1];
			double total=0;
			for(int z=1;z<=CATEGORY;z++){
				items = itemSets[u].iterator();
				while(items.hasNext()){
					int i = items.next();
					total+=postProb[u][i][z];
					p[z]+=postProb[u][i][z];
				}
			}
			for(int z=1;z<=CATEGORY;z++){
				prob_uz[u][z]=p[z]/total;
			}
		}
		//计算参数 σy,z （deviation） 其中用到了Uy,z，所以要先计算
		for(int i=1;i<=ITEMNUM;i++){
			if(userSets[i].size()==0) continue;
			for(int z=1;z<=CATEGORY;z++){
				double numerator = 0;
				double nominator = 0;
				for(int u=1;u<=USERNUM;u++){
					if(rateMatrix[u][i]==0) continue;
					double diff = (rateMatrix[u][i]-mean[i][z])*(rateMatrix[u][i]-mean[i][z]);
					numerator+=(postProb[u][i][z]*diff);
					nominator+=postProb[u][i][z];
				}
				if(nominator<NEARZERO || numerator<NEARZERO) continue; 
				deviation[i][z]=(numerator/nominator);
//				if(deviation[i][z]<(NEARZERO*NEARZERO)){
//					Util.p("rateMatrix[u][i]"+rateMatrix[u][i]+"mean[i][z]"+mean[i][z]
//					                         +"postProb[u][i][z]"+postProb[u][i][z]);
//				}
			}
		}
		//计算参数，Uy,z
		for(int i=1;i<=ITEMNUM;i++){
			if(userSets[i].size()==0) continue;
			for(int z=1;z<=CATEGORY;z++){
				double value = 0;
				double prob = 0;
				for(int u=1;u<=USERNUM;u++){
					if(rateMatrix[u][i]==0) continue;
					value+=postProb[u][i][z]*rateMatrix[u][i];
					prob+=postProb[u][i][z];
				}
				///////////NEARZERO
				if(prob<NEARZERO)	continue;
				mean[i][z]=(value/prob);
			}
		}
		
	}
	
	//计算成本函数
	private double computeLostFunction(){
		double lost = 0;
		for(int u=1;u<=USERNUM;u++){
			Iterator<Integer> items = itemSets[u].iterator();
			while(items.hasNext()){
				int i = items.next();
				double rst=0;
				for(int z=1;z<=CATEGORY;z++){
					//pvy值为NULL
					double pvy=0;
					double pz = prob_uz[u][z];
					//注意有些项目只有一两个用户评了分，方差为0
					if(deviation[i][z]<=NEARZERO || pz<=NEARZERO) continue;
					if(pz<=NEARZERO) pz+=NEARZERO;
					double normal = (rateMatrix[u][i]-mean[i][z])/Math.sqrt(deviation[i][z]);
					pvy=Util.Ni(normal);	//此处pvy可能为0，因为标准正态分布小于-9的概率就是基本为0了。
					if(pvy>NEARZERO) rst+=(postProb[u][i][z]*(Math.log(pvy)+Math.log(pz)));
					
					/*if(Double.isNaN(postProb[u][i][z]*(Math.log(pvy)+Math.log(pz)))){
						Util.p(rateMatrix[u][i]+"><"+mean[i][z]+"><"+deviation[i][z]+"><"+Math.sqrt(deviation[i][z])+"<");
						Util.p(normal);
						Util.p(pz+" --"+pvy+"......");
						Util.p("log: "+Math.log(pz)+" --"+Math.log(pvy)+"......");
					}*/
				}
				lost+=rst;
			}
		}
		Util.p("似然函数值："+lost);
		return lost;
	}
	
	public static void main(String[] args) {
		DataSet d = new DataSet("E:\\数据挖掘数据\\ml-100k\\u_time.base");
		Plsa plsa = new Plsa(d,35);
		plsa.trainModel();
		DataSet t = new DataSet("E:\\数据挖掘数据\\ml-100k\\u_time.test");
		Evaluator e = new Evaluator();
		e.setDataSet(t);
		e.getMAEAndRMSE(plsa);
	}
	

}
