import com.cr7.dataSet.DataSet;
import com.cr7.util.Util;


public class TestUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Util.mark();
		DataSet d = new DataSet("E:\\数据挖掘数据\\ml-100k\\u2.base","train");
		Util.p(d.testUserNum);
		DataSet t = new DataSet("E:\\数据挖掘数据\\ml-100k\\u2.test","test");
		Util.p(t.testUserNum);
		Util.mark("main");
		/*float [] a = {9,0.1f,1.3f,3,12};
		Util.printArray(a);
		Util.sortArray(a);
		Util.printArray(a);*/
		/*byte a =2;
		byte b = 5;
		Util.p(a*b);*/
	}

}
