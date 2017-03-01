package cn.edu.pku.rec.logit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.util.HanLPSegmenter;

public class SortDocs {
	TfIdfcomputor tfidf;
	//切词
	SortDocs() throws IOException
	{
//		PreProcessor.loadSegmenter();
//		PreProcessor.loadStopWords(null);
		HanLPSegmenter.loadStopword(null);
		tfidf = new TfIdfcomputor();
	}
	public List<String> cutWords(String s) throws IOException
	{
		List<String> res = new ArrayList<String>();
		for(String i: HanLPSegmenter.segmentation(s, false, false, null))
		{
			res.add(i);
		}
		return res;
	}
	
	//排序 接口已经定义好
	public List<String> sortDocs( List<List<String>> a, List<String> terms) throws IOException
	{
		List<List<String>> docs = new ArrayList<List<String>>();
		List<String> d = new ArrayList<String>();
		for(List<String> i:a)
		{
			for(String t:i)
			{
				d.add(t);
				System.out.println(t);
			}
		}
		
		for(String i:d)
		{
			List<String> l = cutWords(i);
			for(int j = 0; j < l.size(); j ++)
			{
				if(l.get(j) == null || l.get(j).equals("")||l.get(j).equals("\n"))
				{
					l.remove(j);
				}
			}
			docs.add(l);
		}
		for(List<String> i:docs)
		{
			System.out.println("Start");
			for(String j:i)
				System.out.println(j);
			System.out.println("end");
		}
		d = tfidf.SortDoc(d, docs, terms);
		return d;
		
	}
	
	public List<String> toList(String[] a)
	{
		List<String> res = new ArrayList<String>();
		for(String i : a)
		{
			res.add(i);
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException
	{
		SortDocs s = new SortDocs();
		String[] a = {"我爱北京天安门","天门门前啥都没有","就爱看毛主席","爱看韩国棒子"};
		String[] b = {"棒子大军","韩国棒子最傻B","韩国棒子爱骑小电动"};
		String[] t = {"爱","棒子"};
		List<String> a2 = s.toList(a);
		List<String> b2 = s.toList(b);
		List<String> terms = s.toList(t);
//		for(String i: a2)
//			System.out.println(i);
		List<List<String>> docs = new ArrayList<List<String>>();
		docs.add(a2);
		docs.add(b2);
		
		List<String> d = s.sortDocs(docs,terms);
		for(String i:d)
		{
			System.out.println(i);
		}
		return ;
	}
	
}
