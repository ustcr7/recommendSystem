import com.cr7.model.BuildTrust;
import com.cr7.util.Util;


public class Test {

	public static void main(String[] args) {
		int unum = 3118;
		BuildTrust bt = new BuildTrust("E:\\数据挖掘数据\\epinions\\filterData\\trustSelected.txt",unum);
		Util.mark();
		for(int i=46;i<=unum;i++){
			float [] trust = bt.getTrust(i);
			for(int j=1;j<=unum;j++){
				Util.save(i,j,trust[j]);
			}
			Util.p(i);	//if(i%100==0)
		}
		Util.mark("getTrust");
		
	}

}
