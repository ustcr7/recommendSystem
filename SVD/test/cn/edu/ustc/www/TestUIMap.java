package cn.edu.ustc.www;

import java.util.Map;

public class TestUIMap {
	public static void main(String args[]){
		DataSet ds = new DataSet("E:\\F\\IT\\DataMining\\movielens\\ml-100k\\u1.test");
		UIMap u = new UIMap(ds);
		Map scoreMap = u.getUI();
		Pair p = new Pair();
		int i=0;
		System.out.println(ds.RATINGNUM);
		for(Object o : scoreMap.keySet()){
			i++;
			p = (Pair)o;
			if(i<10) System.out.print(p.u+" "+p.i+": "+scoreMap.get(o)+"  |  ");
		}
		System.out.print("\n"+i);
	}
}
