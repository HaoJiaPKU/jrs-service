package cn.edu.pku.rec.gbdt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.edu.pku.util.FileInput;
import cn.edu.pku.util.FileOutput;

public class SimilarityProcessor {

	public HashMap<String, String> tree
		= new HashMap<String, String>();
	public HashMap<String, ArrayList<String>> dict
		= new HashMap<String, ArrayList<String>>();
	public HashMap<String, HashMap<String, Double>> simMap
		= new HashMap<String, HashMap<String, Double>>();
	public HashMap<String, ArrayList<HashMap<String, Double>>> sim
		= new HashMap<String, ArrayList<HashMap<String, Double>>>();
	public final double w1 = 0.5, w2 = 0.5, w3 = 0.9;

	public void load(String modelPath) {
		FileInput fi = new FileInput(modelPath);
		ObjectMapper om = new ObjectMapper();
		try {
			String content = new String();
			String line = null;
			while((line = fi.reader.readLine()) != null) {
				content += line;
			}
			SimilarityProcessor sp = om.readValue(content, SimilarityProcessor.class);
			this.sim = (HashMap<String, ArrayList<HashMap<String, Double>>>) sp.sim.clone();
			this.dict = (HashMap<String, ArrayList<String>>) sp.dict.clone();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
	}
}