import com.cr7.dataSet.DataSet;
import com.cr7.model.Plsa;


public class TestModel {
	public static void main(String[] args) {
		DataSet d = new DataSet("E:\\数据挖掘数据\\ml-100k\\u_time.base");
		Plsa plsa = new Plsa(d,20);
		plsa.trainModel();
	}
}
