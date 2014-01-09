package cn.edu.ustc.www;

public abstract class Model {
	public double [][] psMatrix; //predict user's rating score;
	public abstract void init();
	public abstract void train();
	public abstract void predictAll();
}
