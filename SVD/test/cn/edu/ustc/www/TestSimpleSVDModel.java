package cn.edu.ustc.www;

public class TestSimpleSVDModel {
	public static void testInit(){
		DataSet train = new DataSet("E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.base");
		SimpleSVDModel svd = new SimpleSVDModel(train);
		svd.init();
		/*p(svd.userNum);
		p(svd.itemNum);
		p(svd.UFMatrix[2][0]);
		p(svd.scoreMatrix[943][31]);*/
		svd.train();
		svd.predictAll();
		
		
	}
	 
	
	public static void testOther(){
		double error=2;
		int a=3;
		error+=(a*=a);
		p(error);
	}
	
	public static void main(String args[]){
		testInit();
		//testOther();
	}
	public static void p(Object o){
		System.out.println(o);		
	}
	public static void p2(Object o){
		System.out.print(o+" ");		
	}
}

