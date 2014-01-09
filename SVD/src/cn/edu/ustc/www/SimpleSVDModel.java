package cn.edu.ustc.www;

public class SimpleSVDModel extends Model {
	public int[][] scoreMatrix;	//use[i]'s rating score on item[j]
	public double [][] psMatrix; //predict user's rating score;
	public double [][] UFMatrix; //user feature matrix;
	public double [][] IFMatrix; //item feature matrix;
	public double alpha = 0.009;	//learning step
	public double lambda = 0.04;	// regularization  coefficient
	public int F=60;   //feature number;
	public int userNum;
	public int itemNum;
	public int MAX_ITERATION_STEP=60;
	private double u;
	private double [] bu;
	private double [] bi;
	public DataSet trainDataSet;
	public SimpleSVDModel(DataSet d){
		trainDataSet = d;
	}
	@Override
	public void init() {
		scoreMatrix = trainDataSet.getScoreMatrix();
		userNum = trainDataSet.getUserNum();
		itemNum = trainDataSet.getItemNum();
		u = trainDataSet.TOTALAVERAGE;
		System.out.println(u);
		bu = new double[userNum+1];
		bi = new double[itemNum+1];
		UFMatrix = new double [userNum+1][F];
		IFMatrix = new double [itemNum+1][F]; 		
	}
	
	@Override
	public void train() {	//offline
		double learnSpeed = this.alpha;
		double range = Math.sqrt(trainDataSet.getTotalAverage()/F);
		Util.initMatrix(UFMatrix,range);	//initial user item feature matrix with 0
		Util.initMatrix(IFMatrix,range); 
		Util.initMatrix(bu);
		Util.initMatrix(bi);
		for(int step=0;step<this.MAX_ITERATION_STEP;step++){	
			//System.out.print("step:"+step);
			for(int u=1;u<scoreMatrix.length;u++){	
				for(int i=1;i<scoreMatrix[0].length;i++){
					if(scoreMatrix[u][i]!=0){
						double eui = scoreMatrix[u][i]-Util.multiply(UFMatrix[u], IFMatrix[i])-u-bu[u]-bi[i];
						eui*=2;
						for(int k=0;k<F;k++){
							double puk = UFMatrix[u][k];
							UFMatrix[u][k]+=(learnSpeed*(eui*IFMatrix[i][k]-lambda*UFMatrix[u][k]));
							IFMatrix[i][k] += (learnSpeed*(eui*puk-lambda*IFMatrix[i][k])); 
							//强制规定在0到5之间
							UFMatrix[u][k]=(UFMatrix[u][k]<0)?(Math.random()*0.0001):UFMatrix[u][k];
							UFMatrix[u][k]=(UFMatrix[u][k]>5)?5:UFMatrix[u][k]; 
							//强制规定在0到5之间
							IFMatrix[i][k]=(IFMatrix[i][k]<0)?(Math.random()*0.0001):IFMatrix[i][k];
							IFMatrix[i][k]=(IFMatrix[i][k]>5)?5:IFMatrix[i][k];
						}				
						bu[u]+=learnSpeed*(eui-lambda*bu[u]);
						bi[i]+=learnSpeed*(eui-lambda*bi[i]);
						learnSpeed*=0.97;
					}
				}
			}
		}
		
	}	
	
	public void predictAll() {
		psMatrix = new double[trainDataSet.getUserNum()+1][trainDataSet.getItemNum()+1];
		Util.initMatrix(this.psMatrix);
		for(int u=1;u<=this.userNum;u++){
			for(int i=1;i<=this.itemNum;i++){				
				if(scoreMatrix[u][i]==0) psMatrix[u][i] = predict(u,i);
			}
		}
	}
	public double predict(int uid,int iid) {	//online
		 return psMatrix[uid][iid] = Util.multiply(UFMatrix[uid], IFMatrix[iid]); 
	}
	
	public double [][] getPMatrix(){
		this.predictAll();
		return this.psMatrix;
	}
	


}
