package cn.edu.pku.rec.logit;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import cn.edu.pku.util.FilePath;

/**
* @author jh@pku
* version 1.0
**/

public class Logistic {
	private double rate;
	private double[][] weights;
	private int ITERATIONS = 1;
	public int k;
	public Logistic(int k,int n) {
		this.rate = 0.0001;
		this.k = k;
		weights = new double[k][n];
	}
	
	
	public void train(List<Instance> instances) throws IOException {
		FileOutputStream t1 = new FileOutputStream(new File(FilePath.nlpPath+"classify.txt"));
		OutputStreamWriter t2 = new OutputStreamWriter(t1, "UTF-8");
		BufferedWriter t3 = new BufferedWriter(t2);
		int m = instances.size();
		for (int n=0; n<ITERATIONS; n++) {
			//double lik = 0.0;
			for(int i=0; i<instances.size(); i++){
				int[] x = instances.get(i).getX();
				//System.out.println(x.length);
				int label = instances.get(i).getLabel();
				for(int l=0;l<k&&l!=label;l++){
					weights[l][0] = weights[l][0] + 1.0 / m * rate * (0.0-p(x,l));
					for(int j=1; j<weights[l].length; j++) {
						weights[l][j] = weights[l][j] + 1.0 / m * rate * (0.0-p(x,l)) * x[j-1];
					}
				}					
				weights[label][0] += 1.0/m * rate * (1.0-p(x,label));
				for(int j=1; j<weights[label].length; j++) {
					weights[label][j] = weights[label][j] +1.0/m * rate * (1.0-p(x,label)) * x[j-1];
				}
				//lik += label * Math.log(classify(x,label)) + (1 - label) * Math.log(1-classify(x,label));
			}
            //System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
		}
		for(int i=0; i<k; i++){
			for(int j=0;j<weights[i].length;j++){
				t3.write(weights[i][j]+ " ");
			}
			t3.newLine();
		}
		t3.close();
	}
	private double p(int[] x, int j){
		double sum=0.0;
		for(int i=0;i<k;i++){
			sum+=Math.exp(classify(x,i));
		}
		double average;
		average=Math.exp(classify(x,j))/sum;
		return average;
	}
	private double classify(int[] x, int label) {
		double logit = weights[label][0];
		for (int i=1; i<weights[label].length; i++) {
			logit += weights[label][i] * x[i-1];
		}
		return logit;
	}
	
	public static void main(String... args) throws IOException {
		List<Instance> instances = DataSet.readDataSet(FilePath.nlpPath+"vector.txt");
		//System.out.println(instances.size());
		Logistic logistic = new Logistic(15, 41);
		logistic.train(instances);		
	}
}