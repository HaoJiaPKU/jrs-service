package cn.edu.pku.rec.logistic;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataSet {

	public static List<Instance> readDataSet(String file) throws IOException{
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(isr);
		String line = null;
		List<Instance> dataset = new ArrayList<Instance>();
		while((line=reader.readLine())!=null) {
			String [] columns = line.split(" ");
			//System.out.println(columns[0]);
			int[] data = new int[columns.length-1];
			int i=1;
			for(i=1; i<columns.length; i++){
				data[i-1] = Integer.parseInt(columns[i]);
			}
			String [] position = {"java","php", "android","c","web",".net","ios","linux","c++","c#","javascript","oracle","mysql","sql","html"};
			for (int j=0;j<15;j++){
				if(columns[0].equals(position[j])){
					int label = j;
					//System.out.println(label);
					Instance instance = new Instance(label, data); 
					dataset.add(instance);
					break;
				}
			}
		}
		reader.close();
		//System.out.println(size);
		return dataset;
	}
}
