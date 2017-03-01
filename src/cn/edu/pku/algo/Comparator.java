package cn.edu.pku.algo;

import java.util.HashMap;

public class Comparator {

	public double compare(HashMap<String, Double> probs1, HashMap<String, Double> probs2) {
		return 1.0 - relativeEntropy(probs1, probs2);
	}
	
	public double relativeEntropy(HashMap<String, Double> probs1, HashMap<String, Double> probs2) {
		double res = Math.log(4.0);
		for (String feature : probs1.keySet()) {
			if (probs2.containsKey(feature)) {
				double p1 = probs1.get(feature);
				double p2 = probs2.get(feature);
				if (p1 > 0.0 && p2 > 0.0) {
					res += p1 * Math.log(p1 / (p1 + p2))
							+ p2 * Math.log(p2 / (p1 + p2));
				}
			}
		}
		return Math.exp(res) / (1.0 + Math.exp(res));
	}
}
