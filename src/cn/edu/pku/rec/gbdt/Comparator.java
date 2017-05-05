package cn.edu.pku.rec.gbdt;

import java.util.ArrayList;
import java.util.HashMap;

public class Comparator {

	public double compare(
			Instance resumeIns,
			Instance positionIns,
			HashMap<String, Double> distributionResume,
			HashMap<String, Double> distributionPosition,
			HashMap<String, ArrayList<HashMap<String, Double>>> sim) {
		double alpha = 1.0;
		
//		HashMap<String, Double> resumeWeight = new HashMap<String, Double>();
//		HashMap<String, Double> positionWeight = new HashMap<String, Double>();
//		double sum = 0.0;
		HashMap<String, Double> resumeWeight = (HashMap<String, Double>) resumeIns.numTypeFeature.clone();
		HashMap<String, Double> positionWeight = (HashMap<String, Double>) positionIns.numTypeFeature.clone();
		HashMap<String, Double> unmatch = new HashMap<String, Double>();
//		for (String word : resumeWeight.keySet()) {
//			System.out.print(word + "	" + resumeWeight.get(word) + "	");
//		}
//		System.out.println();
		for (String word : resumeWeight.keySet()) {
//			sum += resumeWeight.get(word);
			if (resumeWeight.get(word) > 0 && !positionWeight.containsKey(word)) {
				double counter = 0;
				for (int i = 0; i < sim.get(word).size(); i ++) {
					for (String str : sim.get(word).get(i).keySet()) {
						if (positionWeight.containsKey(str)) {
							counter += 1.0;
						}
					}
				}
				if (counter > 0) {
					unmatch.put(word, counter);
				}
			}
		}
//		for (String word : resumeWeight.keySet()) {
//			System.out.print(word + "	" + resumeWeight.get(word) + "	");
//		}
//		System.out.println();
		for (String word : unmatch.keySet()) {
			for (int i = 0; i < sim.get(word).size(); i ++) {
				for (String str : sim.get(word).get(i).keySet()) {
					if (positionWeight.containsKey(str)) {
						double counter = resumeWeight.get(str);
						resumeWeight.put(str, counter + resumeWeight.get(word) * (1.0 / unmatch.get(word)) * sim.get(word).get(i).get(str));
					}
				}
			}
			resumeWeight.put(word, 0.0);
		}
//		for (String word : positionWeight.keySet()) {
//			System.out.print(word + "	" + positionWeight.get(word) + "	");
//		}
//		System.out.println();
//		sum += 0.000001;
//		for (String word : resumeWeight.keySet()) {
//			resumeWeight.put(word, resumeWeight.get(word) / sum);
//		}
//		sum = 0.0;
//		for (String word : positionWeight.keySet()) {
//			sum += positionWeight.get(word);
//		}
//		sum += 0.000001;
//		for (String word : positionWeight.keySet()) {
//			positionWeight.put(word, positionWeight.get(word) / sum);
//		}
		
		return Math.pow(vsmMatch(resumeWeight, positionWeight), alpha)
			* Math.pow(relativeEntropyForProbability(distributionResume, distributionPosition), (1.0 - alpha));
	}
	
	public double wordMatch(HashMap<String, Double> resumeWeight, HashMap<String, Double> positionWeight) {
		double score = 0.0, sumOfScore = 0.0;
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
					sumOfScore += 1.0;
				}
			}
		}
		if (sumOfScore == 0.0) {
			return 0;
		}
		return score / sumOfScore;
	}
	
	public double vsmMatch(HashMap<String, Double> resumeWeight, HashMap<String, Double> positionWeight) {
		double score = 0.0, sumOfScore = 0.0;
		double sumsqr = 0.0, sumsqp = 0.0;
		for (String word : resumeWeight.keySet()) {
			if (resumeWeight.get(word) != 0
					&& positionWeight.containsKey(word)
					&& positionWeight.get(word) != 0) {
				score += resumeWeight.get(word) * positionWeight.get(word);
			}
		}
		for (String word : resumeWeight.keySet()) {
			sumsqr += resumeWeight.get(word) * resumeWeight.get(word);
		}
		for (String word : positionWeight.keySet()) {
			sumsqp += positionWeight.get(word) * positionWeight.get(word);
		}
		sumOfScore = Math.pow(sumsqr, 0.5) * Math.pow(sumsqp, 0.5);
		if (sumOfScore == 0.0) {
			return 0;
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
		res = res / Math.log(4.0);
		return 1.0 - res;
	}
}
