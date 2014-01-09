package com.cr7.model;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cr7.util.Util;

public class BuildTrust {
	private Vector<Integer> [] trustU2U;
	private int UNUM;	
	private int STEP=4;	//信任传播最远步数
	public BuildTrust(String file,int usernum){
		UNUM = usernum;
		trustU2U = new Vector[UNUM+1];
		for(int i=0;i<=UNUM;i++){
			trustU2U[i] = new Vector<Integer>();
		}
		initTrust(file);
	}
	//根据trust文件，将各用户直接评价的trust用户放到Vector里面
	public void initTrust(String file){
		HashMap<Integer,Integer> usermap = new HashMap<Integer,Integer>();
		try{
			BufferedReader bf = Util.getBufferedReader(file);
			String line = "";
			int u1=0;	int u2=0;	
			while((line=bf.readLine())!=null){
				Pattern p = Pattern.compile("[\\w]+");
				Matcher m = p.matcher(line);
				if(m.find()) u1=Integer.parseInt(m.group());
				if(m.find()) u2=Integer.parseInt(m.group());
				usermap.put(u1, u2);
				trustU2U[u1].add(u2);
			}
//			//把map里的数据放到每个用户的Vector里面
//			for(Map.Entry<Integer,Integer> entry : usermap.entrySet()){
//				trustU2U[entry.getKey()].add(entry.getValue());
//			}
		}catch(Exception e){}
	}
	//通过传播，获得用户对其他用户的trust值,最大传播次数为4
	public float [] getTrust(int u){
		float trustInfo [] = new float[UNUM+1];
		HashSet<Integer> old  = new HashSet<Integer>();
		Vector<Integer> step2 = new Vector<Integer> ();
		trustInfo[u]=1;old.add(u);
		for(int i=0;i<trustU2U[u].size();i++){
			old.add(trustU2U[u].get(i));
		}
		for(int i=0;i<trustU2U[u].size();i++){
			int v = trustU2U[u].get(i);
			trustInfo[v]=1;
			for(int j=0;j<trustU2U[v].size();j++){
				int p=trustU2U[v].get(j);
				if(! old.contains(p)) step2.add(p);
			}
		}
//		Util.p("\n第一次扩散\n");
//		Util.p(trustInfo);
		Vector<Integer> step3 = new Vector<Integer> ();
		for(int i=0;i<step2.size();i++){
			old.add(step2.get(i));
		}
		for(int i=0;i<step2.size();i++){
			int v = step2.get(i);
			trustInfo[v]=0.75f;
			for(int j=0;j<trustU2U[v].size();j++){
				int p=trustU2U[v].get(j);
				if(! old.contains(p)) step3.add(p);
			}
		}
//		Util.p("\n第二次扩散\n");
//		Util.p(trustInfo);
		Vector<Integer> step4 = new Vector<Integer> ();
		for(int i=0;i<step3.size();i++){
			old.add(step3.get(i));
		}
		for(int i=0;i<step3.size();i++){
			int v = step3.get(i);
			trustInfo[v]=0.5f;
			old.add(v);
			for(int j=0;j<trustU2U[v].size();j++){
				int p=trustU2U[v].get(j);
				if(! old.contains(p)) step4.add(p);
			}
		}
//		Util.p("\n第三次扩散\n");
//		Util.p(trustInfo);
		for(int i=0;i<step4.size();i++){
			int v = step4.get(i);
			trustInfo[v]=0.25f;
		}
//		Util.p("\n第四次扩散\n");
//		Util.p(trustInfo);
		return trustInfo;
	}

}
