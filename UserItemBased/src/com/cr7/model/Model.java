package com.cr7.model;


public interface Model {
	public double predict(int u,int i);	
	public int [] getTopN(int u,int n);
}
