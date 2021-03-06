package com.cr7.bak;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.cr7.dataSet.DataSet;
import com.cr7.evaluate.Evaluator;
import com.cr7.util.Util;

public class Plsa1 {
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
	private int MAX_LOOP=15;
	public static final double NEARZERO=0.00000000001;
	public Plsa1(DataSet d,int category){
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
//		Util.writeIntoTxt(mean,"E:\\u.txt");
		//方差参数设为1.5上下浮动0.5
		for(int i=1;i<=ITEMNUM;i++){
			for(int z=1;z<=CATEGORY;z++){
				deviation[i][z]=(2-(new Random().nextDouble()));
			}
		}
//		Util.writeIntoTxt(deviation,"E:\\dev.txt");
		//参数--p(z|u)设置为1/category
		for(int u=1;u<=USERNUM;u++){
			for(int z=1;z<=CATEGORY;z++){
				prob_uz[u][z]=1/(double)CATEGORY;
//				Util.p(prob_uz[u][z]);
			}
		}
//		Util.writeIntoTxt(prob_uz,"E:\\puz.txt");
	}
	
	public void trainModel(){
		int count=0;
	Util.mark();
		while(count<MAX_LOOP){
			computePostProbability();
	Util.mark("computePostProbability");
			computeParameter();
	Util.mark("computeParameter");
			computeLostFunction();
	Util.mark("computeLostFunction");
			count++;
			for(int u=1;u<=250;u++){
				for(int i=1;i<=450;i++){
					Util.writeIntoTxt(postProb[u][i],"E:\\postProb"+count+".txt");
				}
			}
			Util.writeIntoTxt(mean,"E:\\mean"+count+".txt");
			Util.writeIntoTxt(deviation,"E:\\devia"+count+".txt");
			Util.writeIntoTxt(prob_uz,"E:\\puz"+count+".txt");
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
					if(deviation[i][z]<NEARZERO) deviation[i][z]+=NEARZERO;
					double p = (rateMatrix[u][i]-mean[i][z])/deviation[i][z];
					pv[z] = prob_uz[u][z]*Util.Ni(p);
					total+=(pv[z]);
				}
				//注意total==0
				for(int z=1;z<=CATEGORY;z++){
					if(total<NEARZERO) total+=NEARZERO;
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
				if(total<NEARZERO) total+=NEARZERO;
				prob_uz[u][z]=p[z]/total;
			}
		}
		//计算参数 σy,z （deviation） 其中用到了Uy,z，所以要先计算
		for(int i=1;i<=ITEMNUM;i++){
			if(userSets[i].size()==0) continue;
//			Iterator<Integer> users = userSets[i].iterator();
			double numerator = 0;
			double nominator = 0;
			for(int z=1;z<=CATEGORY;z++){
				for(int u=1;u<=USERNUM;u++){
					double diff = (rateMatrix[u][i]-mean[i][z])*(rateMatrix[u][i]-mean[i][z]);
					numerator+=(postProb[u][i][z]*diff);
					nominator+=postProb[u][i][z];
				}
				///////////NEARZERO
				if(nominator>NEARZERO) nominator+=NEARZERO; 
				deviation[i][z]=(numerator/nominator);
			}
		}
		//计算参数，Uy,z
		for(int i=1;i<=ITEMNUM;i++){
			if(userSets[i].size()==0) continue;
//			Iterator<Integer> users = userSets[i].iterator();
			double value = 0;
			double prob = 0;
			for(int z=1;z<=CATEGORY;z++){
				for(int u=1;u<=USERNUM;u++){
					value+=postProb[u][i][z]*rateMatrix[u][i];
					prob+=postProb[u][i][z];
				}
				///////////NEARZERO
				if(prob>NEARZERO)	prob+=NEARZERO; 
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
//					if(deviation[i][z]<=NEARZERO && pz<=NEARZERO) continue;
					if(deviation[i][z]<NEARZERO) deviation[i][z]+=NEARZERO;
					if(pz<=NEARZERO) pz+=NEARZERO;
					pvy=Util.Ni((rateMatrix[u][i]-mean[i][z])/Math.sqrt(deviation[i][z]));
					rst+=(postProb[u][i][z]*(Math.log(pvy)+Math.log(pz)));
				}
				lost+=rst;
			}
		}
		Util.p("似然函数值："+lost);
		return lost;
	}
	

}
