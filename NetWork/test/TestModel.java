import com.cr7.dataSet.DataSet;
import com.cr7.evaluate.Evaluator;
import com.cr7.model.BipartiteNetwork;
import com.cr7.model.Model;
import com.cr7.util.Util;


public class TestModel {

	public static void main(String[] args) {
		//预测准确率时记得更改Evaluator中的变量testUserNum
		
		String train="E:\\数据挖掘数据\\ml-100k\\u1.base";
		String test="E:\\数据挖掘数据\\ml-100k\\u1.test";
		Util.p("u1");
//		testR(train,test);
		testBN(train,test,20);
		testBN(train,test,30);
		testBN(train,test,40);
		testBN(train,test,50);
		/*	testBNM(train,test,20);
		testBNM(train,test,30);
		testBNM(train,test,40);
		testBNM(train,test,50);
		testBNM(train,test,100);*/
}
	public static void testR(String trainFile,String testFile ){
		DataSet d = new DataSet(trainFile,"train");
		Util.mark();
		Model m =new BipartiteNetwork(d);
		Util.mark("init model");
		DataSet dt = new DataSet(testFile,"test");
		Evaluator e = new Evaluator(dt);
		e.getR(m);
	}
	public static void testBNM(String trainFile,String testFile ,int n){
			long s = System.currentTimeMillis();
			DataSet d = new DataSet(trainFile,"train");
			Util.mark();
			Model m =new BipartiteNetwork(d);
			Util.mark("init model");
			/*Util.printArray(m.getTopN(1, n));
			Util.mark("get topn of 1");
			Util.printArray(m.getTopN(2, n));
			Util.mark("get topn of 2");*/
			DataSet dt = new DataSet(testFile,"test");
			Evaluator e = new Evaluator(dt);
//			Util.p("总命中率："+e.getHitRate(m, n));
			Util.p("邻居数目"+n+" 准确率"+e.getPrecision(m, n)+"**\r\n");
			Util.p((System.currentTimeMillis()-s)/60000);
			
		}

	public static void testBN(String trainFile,String testFile ,int n){
		long s = System.currentTimeMillis();
		DataSet d = new DataSet(trainFile,"train");
		Model m =new BipartiteNetwork(d);
		
		DataSet dt = new DataSet(testFile,"test");
		Evaluator e = new Evaluator(dt);
		Util.p("总命中率："+e.getHitRate(m, n));
		Util.p((System.currentTimeMillis()-s)/60000);
		Util.p("邻居数目"+n+" 准确率"+e.getPrecision(m, n)+"**\r\n");
		
	}
	
	
}
