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
		
//		HashMap<String, Double> resumeVector = new HashMap<String, Double>();
//		HashMap<String, Double> positionVector = new HashMap<String, Double>();
//		double sum = 0.0;
		HashMap<String, Double> resumeVector = (HashMap<String, Double>) resumeIns.numTypeFeature.clone();
		HashMap<String, Double> positionVector = (HashMap<String, Double>) positionIns.numTypeFeature.clone();
		HashMap<String, Double> unmatch = new HashMap<String, Double>();
//		for (String word : resumeVector.keySet()) {
//			System.out.print(word + "	" + resumeVector.get(word) + "	");
//		}
//		System.out.println();
		for (String word : resumeVector.keySet()) {
//			sum += resumeVector.get(word);
			if (resumeVector.get(word) > 0 && !positionVector.containsKey(word)) {
				double counter = 0;
				for (int i = 0; i < sim.get(word).size(); i ++) {
					for (String str : sim.get(word).get(i).keySet()) {
						if (positionVector.containsKey(str)) {
							counter += 1.0;
						}
					}
				}
				if (counter > 0) {
					unmatch.put(word, counter);
				}
			}
		}
//		for (String word : resumeVector.keySet()) {
//			System.out.print(word + "	" + resumeVector.get(word) + "	");
//		}
//		System.out.println();
		for (String word : unmatch.keySet()) {
			for (int i = 0; i < sim.get(word).size(); i ++) {
				for (String str : sim.get(word).get(i).keySet()) {
					if (positionVector.containsKey(str)) {
						double counter = resumeVector.get(str);
						resumeVector.put(str, counter + resumeVector.get(word) * (1.0 / unmatch.get(word)) * sim.get(word).get(i).get(str));
					}
				}
			}
			resumeVector.put(word, 0.0);
		}
//		for (String word : positionVector.keySet()) {
//			System.out.print(word + "	" + positionVector.get(word) + "	");
//		}
//		System.out.println();
//		sum += 0.000001;
//		for (String word : resumeVector.keySet()) {
//			resumeVector.put(word, resumeVector.get(word) / sum);
//		}
//		sum = 0.0;
//		for (String word : positionVector.keySet()) {
//			sum += positionVector.get(word);
//		}
//		sum += 0.000001;
//		for (String word : positionVector.keySet()) {
//			positionVector.put(word, positionVector.get(word) / sum);
//		}
		
		return Math.pow(vsmMatch(resumeVector, positionVector), alpha)
			* Math.pow(relativeEntropyForProbability(distributionResume, distributionPosition), (1.0 - alpha));
	}
	
	public double wordMatch(HashMap<String, Double> resumeVector, HashMap<String, Double> positionVector) {
		double score = 0.0, sumOfScore = 0.0;
		for (String word : positionVector.keySet()) {
			if (positionVector.get(word) != 0) {
				if (resumeVector.containsKey(word)) {
					double ratio = resumeVector.get(word) / positionVector.get(word);
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
	
	public double vsmMatch(HashMap<String, Double> resumeVector, HashMap<String, Double> positionVector) {
		double score = 0.0, sumOfScore = 0.0;
		double sumsqr = 0.0, sumsqp = 0.0;
		for (String word : resumeVector.keySet()) {
			if (resumeVector.get(word) != 0
					&& positionVector.containsKey(word)
					&& positionVector.get(word) != 0) {
				score += resumeVector.get(word) * positionVector.get(word);
			}
		}
		for (String word : resumeVector.keySet()) {
			sumsqr += resumeVector.get(word) * resumeVector.get(word);
		}
		for (String word : positionVector.keySet()) {
			sumsqp += positionVector.get(word) * positionVector.get(word);
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
