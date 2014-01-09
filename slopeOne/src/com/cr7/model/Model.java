package com.cr7.model;

import com.cr7.dataset.DataSet;

public interface Model {
	public void setDataSet(DataSet d);
	public double predict(int userId,int itemId);
}
