package cn.edu.pku.rec.gbdt;

import java.util.HashMap;

import cn.edu.pku.gbdt.Instance;

public class Comparator {

	public double compare(
			Instance resumeIns,
			Instance positionIns,
			HashMap<String, Double> distributionResume,
			HashMap<String, Double> distributionPosition) {
		double alpha = 0.45;
		
		HashMap<String, Double> resumeWeight = new HashMap<String, Double>();
		HashMap<String, Double> positionWeight = new HashMap<String, Double>();
		double sum = 0.0;
		HashMap<String, Double> t = resumeIns.numTypeFeature;
		for (String word : t.keySet()) {
			sum += t.get(word);
		}
		sum += 0.000001;
		for (String word : t.keySet()) {
			resumeWeight.put(word, t.get(word) / sum);
		}
		sum = 0.0;
		t = positionIns.numTypeFeature;
		for (String word : t.keySet()) {
			sum += t.get(word);
		}
		sum += 0.000001;
		for (String word : t.keySet()) {
			positionWeight.put(word, t.get(word) / sum);
		}
		return wordMatch(resumeWeight, positionWeight) * alpha
			+ (1.0 - relativeEntropyForProbability(distributionResume, distributionPosition)) * (1.0 - alpha);
	}
	
	public double wordMatch(HashMap<String, Double> resumeWeight, HashMap<String, Double> positionWeight) {
		double score = 0.0, sumOfScore = 0.000001;
		for (String word : positionWeight.keySet()) {
			if (positionWeight.get(word) != 0) {
				if (resumeWeight.containsKey(word)) {
					double ratio = resumeWeight.get(word) / positionWeight.get(word);
					if (ratio > 1.0) {
						ratio = 1.0;
					}
					score += ratio;
					sumOfScore += 1.0;
				} else {
					
				}
			}
		}
		return score / sumOfScore;
	}
	
	public double relativeEntropyForProbability(HashMap<String, Double> probs1, HashMap<String, Double> probs2) {
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
