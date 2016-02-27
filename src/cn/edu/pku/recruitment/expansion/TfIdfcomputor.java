package cn.edu.pku.recruitment.expansion;


import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class TfIdfcomputor {
	public double tf(List<String> doc, String term) {
	    double result = 0;
	    for (String word : doc) {
	       if (term.equalsIgnoreCase(word))
	              result++;
	       }
	    return result / doc.size();
	}
	public double idf(List<List<String>> docs, String term) {
	    double n = 0;
	    for (List<String> doc : docs) {
	        for (String word : doc) {
	            if (term.equalsIgnoreCase(word)) {
	                n++;
	                break;
	            }
	        }
	    }
	    return Math.log(docs.size() / n);
	}
		
	public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
	    return tf(doc, term) * idf(docs, term);
	}
	
	public  List<String> SortDoc( List<String> d,List<List<String>> docs, List<String> terms)
	{
		Vector<Double> sims = new Vector<Double>();
		sims.setSize(docs.size());
		int i = 0;
		for(List<String> doc : docs)
		{
			double sim = 0;
			for(String term : terms)
			{
				sim += tfIdf(doc, docs, term);
			}
			sims.set(i, sim);
			i++;
		}
		for(i = 0; i < docs.size(); i ++)
		{
			for(int j = i+1; j < docs.size(); j ++)
			{
				double sim1 = sims.get(i);
				double sim2 = sims.get(j);
				List<String> doc1 = docs.get(i);
				List<String> doc2 = docs.get(j);
				String d1 = d.get(i);
				String d2 = d.get(j);
				if( sim1 < sim2 )
				{
					sims.set(i, sim2);
					sims.set(j, sim1);
					docs.set(i, doc2);
					docs.set(j, doc1);
					d.set(i, d2);
					d.set(j, d1);
				}
			}
		}
		
		for(i = 0; i < docs.size(); i ++)
		{
			System.out.println(sims.get(i));
			System.out.println(d.get(i));
		}
		return d;
	}
	
	public static void main(String[] args) throws IOException {
		 
		List<String> doc1 = (List<String>) Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum","","");
		List<String> doc2 = (List<String>) Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo","","");
		List<String> doc3 = (List<String>) Arrays.asList("Has", "persius", "disputationi", "id", "simul");
		List<List<String>> documents = (List<List<String>>) Arrays.asList(doc1, doc2, doc3);
	    TfIdfcomputor ca = new TfIdfcomputor();
	    List<String> d = new ArrayList<String>();
	    d.add("Lorem");
	    d.add("Vituperata");
	    d.add("has");
	    //double tfidf = ca.tfIdf(doc1, documents, "ipsum");
	    //System.out.println("TF-IDF (ipsum) = " + tfidf);
	    //doc2 = doc1;
	    //System.out.println(doc2.get(1));
	    List<String> terms = (List<String>) Arrays.asList("ipsum","Has");
	    ca.SortDoc(d,documents, terms);
	    
//	    SortDocs s = new SortDocs();
//		String[] a = {"我爱","北京","天安门"};
//		String[] b = {"棒子大军","韩国棒子最傻B","韩国棒子爱骑小电动"};
//		String[] t = {"爱","棒子"};
//		List<String> a2 = s.toList(a);
//		List<String> b2 = s.toList(b);
//		List<String> ts = s.toList(t);
//		for(String i: a2)
//			System.out.println(i);
//		List<List<String>> docs = new ArrayList<List<String>>();
//		docs.add(a2);
//		docs.add(b2);
//		
//		List<String> d = s.sortDocs(docs,ts);
//		for(String i:d)
//		{
//			System.out.println(i);
//		}
	}
}
