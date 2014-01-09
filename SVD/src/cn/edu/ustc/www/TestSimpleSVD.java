package cn.edu.ustc.www;

public class TestSimpleSVD {

	/**
	 * simple SVD, realize with gradient descent method 
	 */
	public static void main(String[] args) {
		DataSet trainData = new DataSet("E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.base");		
		SimpleSVDModel svd = new SimpleSVDModel(trainData);		
		svd.init();
		svd.train();
		svd.predictAll();
		DataSet testData = new DataSet("E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.test");
		Evaluate e = new Evaluate();
		System.out.println("MAE "+e.evaluateMAE(svd, testData));
		System.out.println("RMSE "+e.evaluateRMSE(svd, testData));
	}

}
