package cn.edu.ustc.www;
/**
 * 
 * pair means user-item pair
 *
 */
public class Pair{
	int u;
	int i;
	public Pair(){
		u=0;
		i=0;
	}
	public Pair(int a,int b){
		u=a;
		i=b;
	}
	public int getU() {
		return u;
	}
	public void setU(int u) {
		this.u = u;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
}