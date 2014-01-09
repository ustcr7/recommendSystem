package com.cr7.model;

import java.util.TreeMap;

public interface Model {
	public void computeWeight(int u,float [] weight);
	public double predict(int u,int i);
	public int [] getTopN(int u,int n);
	public TreeMap<Integer,Integer> getAll(int u);
}
