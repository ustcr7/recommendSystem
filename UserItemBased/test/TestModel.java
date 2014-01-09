import com.cr7.dataSet.DataSet;
import com.cr7.evaluate.Evaluator;
import com.cr7.model.Model;
import com.cr7.model.UserBased_1;




public class TestModel {

	public static void main(String[] args) {
		DataSet d = new DataSet("E:\\数据挖掘数据\\epinions\\filterData\\rate_train.txt");
		Model m = new UserBased_1(d,30);
		Evaluator e = new Evaluator(new DataSet("E:\\数据挖掘数据\\epinions\\filterData\\rate_test.txt"));
		e.getMAE_RMSE(m);
	
	}


}
