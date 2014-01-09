package com.cr7.evaluate;
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
	@Override
	public String toString(){
		return "u"+u+" i"+i;
	}
	@Override
	public boolean equals(Object p2){
		return (this.u==((Pair)p2).u)&&(this.i==((Pair)p2).i);
	}
}