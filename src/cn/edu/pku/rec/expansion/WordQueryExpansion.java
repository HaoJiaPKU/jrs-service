package cn.edu.pku.rec.expansion;

import cn.edu.pku.rec.knowledge.KnowledgeBase;
import cn.edu.pku.util.FilePath;

import java.io.IOException;
import java.util.*;


/**
 * @author Lan Zheng
 * @version 1.0
 * */
public class WordQueryExpansion implements QueryExpansion{

	@Override
	public ArrayList <String> similarWords(String input, int num) {
		ArrayList <String> ret = new ArrayList <String> ();
		int skillIndex = 0;
		for(int i = 0; i < KnowledgeBase.skillList.length; i ++) {
			if(KnowledgeBase.skillList[i].equals(input)) {
				skillIndex = i;
				break;
			}
		}
		Map <String, Double> m = new HashMap <String, Double> ();
		for(int i = 0; i < KnowledgeBase.skillList.length; i ++) {
			m.put(KnowledgeBase.skillList[i], KnowledgeBase.skillSimilarity[skillIndex][i]);
		}
		@SuppressWarnings("rawtypes")
		Map.Entry [] set = getSortedHashMapByKey(m);
		for(int i = 0; i < num; i ++) {
//			System.out.println(set[i].getKey() + " " + set[i].getValue());
			ret.add(set[i].getKey().toString());
		}
		return ret;
	}
	

	/**
	 * HashMap排序器
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] getSortedHashMapByKey(Map m)
	{
		Set set = m.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() 
		{
			public int compare(Object arg0, Object arg1) 
			{
		        Object value1 = ((Map.Entry) arg0).getValue();
		        Object value2 = ((Map.Entry) arg1).getValue();
		        return ((Comparable) value2).compareTo(value1);
		    }
		});
		return entries;
	}
	
	public static void main(String [] args) throws IOException {
		KnowledgeBase.setSkillFile(100, FilePath.nlpPath+"skillList top100.txt");
		KnowledgeBase.setSimilFile(FilePath.nlpPath+"similarity matrix.txt");
		KnowledgeBase.loadKnowledgeBase();
		
		WordQueryExpansion wqe = new WordQueryExpansion ();
		wqe.similarWords("java", 10);
	}

}