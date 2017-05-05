package cn.edu.pku.rec.gbdt;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Util {

	public static HashSet<Integer> sample(HashSet<Integer> set, int num) {
		HashSet<Integer> res = new HashSet<Integer>();
		ArrayList<Integer> t = new ArrayList<Integer>();
		for (Integer id : set) {
			t.add(id);
		}
		Random r = new Random();
		for (int i = 0; i < num; i ++) {
			int index = r.nextInt(t.size());
			res.add(t.get(index));
			t.remove(index);
		}
		return res;
	}
	
	public static HashSet<Integer> minusSet(HashSet<Integer> set, HashSet<Integer> subset) {
		HashSet<Integer> res = new HashSet<Integer>();
		for (Integer id : set) {
			if (!subset.contains(id)) {
				res.add(id);
			}
		}
		return res;
	}
	
	public static double computeMinLoss(ArrayList<Double> values) {
		if (values.size() < 2) {
			return 0;
		}
		double mean = 0;
		for (int i = 0; i < values.size(); i ++) {
			mean += values.get(i);
		}
		mean = mean / (double)values.size();
		double loss = 0;
		for (int i = 0; i < values.size(); i ++) {
			loss += (mean - values.get(i)) * (mean - values.get(i));
		}
		return loss;
	}
	
	public static Tree makeDecisionTree(DataSet dataset, HashSet<Integer> remainedSet,
			HashMap<Integer, Double> targets, int depth, HashSet<LeafNode> leafNodes,
			int maxDepth, double splitPoint) {
		if (depth < maxDepth) {
			HashSet<String> attributes = (HashSet<String>) dataset.getAttribute().clone();
			double loss = -1.0;
			String selectedAttribute = null;
			double numConditionValue = 0;
			String strConditionValue = null;
			HashSet<Integer> selectedLeftIdSet = new HashSet<Integer>();
			HashSet<Integer> selectedRightIdSet = new HashSet<Integer>();
			for (String attribute : attributes) {
				boolean isRealType = dataset.isRealTypeField(attribute);
				if(isRealType) {
					HashSet<Double> numAttrValues = dataset.numTypeFeature.get(attribute);
					if (splitPoint > 0 && numAttrValues.size() > splitPoint) {
						//TODO 实现随机抽样函数
					}
					for (Double attrValue : numAttrValues) {
						HashSet<Integer> leftIdSet = new HashSet<Integer>();
						HashSet<Integer> rightIdSet = new HashSet<Integer>();
						for (Integer id : remainedSet) {
							Instance instance = dataset.getInstance(id);
							double value = instance.numTypeFeature.get(attribute);
							if (value < attrValue) {
								leftIdSet.add(id);
							} else {
								rightIdSet.add(id);
							}
						}
						ArrayList<Double> leftTargets = new ArrayList<Double>();
						ArrayList<Double> rightTargets = new ArrayList<Double>();
						for (Integer id : leftIdSet) {
							leftTargets.add(targets.get(id));
						}
						for (Integer id : rightIdSet) {
							rightTargets.add(targets.get(id));
						}
						double sumLoss = computeMinLoss(leftTargets)
								+ computeMinLoss(rightTargets);
						if (loss < 0 || sumLoss < loss) {
							selectedAttribute = new String(attribute);
							numConditionValue = attrValue;
							loss = sumLoss;
							selectedLeftIdSet = (HashSet<Integer>) leftIdSet.clone();
							selectedRightIdSet = (HashSet<Integer>) rightIdSet.clone();
						}
					}
				} else {
					HashSet<String> strAttrValues = dataset.strTypeFeature.get(attribute);
					for (String attrValue : strAttrValues) {
						HashSet<Integer> leftIdSet = new HashSet<Integer>();
						HashSet<Integer> rightIdSet = new HashSet<Integer>();
						for (Integer id : remainedSet) {
							Instance instance = dataset.getInstance(id);
							String value = instance.strTypeFeature.get(attribute);
							if (value.equals(attrValue)) {
								leftIdSet.add(id);
							} else {
								rightIdSet.add(id);
							}
						}
						ArrayList<Double> leftTargets = new ArrayList<Double>();
						ArrayList<Double> rightTargets = new ArrayList<Double>();
						for (Integer id : leftIdSet) {
							leftTargets.add(targets.get(id));
						}
						for (Integer id : rightIdSet) {
							rightTargets.add(targets.get(id));
						}
						double sumLoss = computeMinLoss(leftTargets)
								+ computeMinLoss(rightTargets);
						if (loss < 0 || sumLoss < loss) {
							selectedAttribute = new String(attribute);
							strConditionValue = new String(attrValue);
							loss = sumLoss;
							selectedLeftIdSet = (HashSet<Integer>) leftIdSet.clone();
							selectedRightIdSet = (HashSet<Integer>) rightIdSet.clone();
						}
					}
				}
			}
			if (selectedAttribute == null || loss < 0) {
				System.out.println("cannot determine the split attribute.");
			}
			Tree tree = new Tree();
			tree.splitFeature = new String(selectedAttribute);
			tree.isRealValueFeature = dataset.isRealTypeField(selectedAttribute);
			if (tree.isRealValueFeature) {
				tree.numConditionValue = numConditionValue;
			} else {
				tree.strConditionValue = new String(strConditionValue);
			}
			tree.leftTree = makeDecisionTree(dataset, selectedLeftIdSet, targets,
					depth + 1, leafNodes, maxDepth, 0);
			tree.rightTree = makeDecisionTree(dataset, selectedRightIdSet, targets,
					depth + 1, leafNodes, maxDepth, 0);
			return tree;
		} else {
			LeafNode node = new LeafNode(remainedSet);
			int K = dataset.getLabelSize();
			node.updatePredictValue(targets, K);
			leafNodes.add(new LeafNode(node));
			Tree tree = new Tree();
			tree.leafNode = new LeafNode(node);
			return tree;
		}
	}

	/**
	 * HashMap排序器
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] getSortedHashMapByValueAsc(Map h)
	{
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() 
		{
			public int compare(Object arg0, Object arg1) 
			{
				int value1 = (int) ((Map.Entry) arg0).getValue();
				int value2 = (int) ((Map.Entry) arg1).getValue();
				if (value2 > value1) {
					return -1;
				} else if (value2 == value1) {
					return 0;
				} else {
					return 1;
				}
		    }
		});
		return entries;
	}
	
	/**
	 * HashMap排序器
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] getSortedHashMapByValueDec(Map h)
	{
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() 
		{
			public int compare(Object arg0, Object arg1) 
			{
				int value1 = (int) ((Map.Entry) arg0).getValue();
				int value2 = (int) ((Map.Entry) arg1).getValue();
				if (value2 > value1) {
					return 1;
				} else if (value2 == value1) {
					return 0;
				} else {
					return -1;
				}
		    }
		});
		return entries;
	}
	
	/**
	 * HashMap排序器
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map.Entry[] getSortedHashMapByDoubleValueDec(Map h)
	{
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() 
		{
			public int compare(Object arg0, Object arg1) 
			{
				double value1 = (double) ((Map.Entry) arg0).getValue();
				double value2 = (double) ((Map.Entry) arg1).getValue();
				if (value2 > value1) {
					return 1;
				} else if (value2 == value1) {
					return 0;
				} else {
					return -1;
				}
		    }
		});
		return entries;
	}
}
