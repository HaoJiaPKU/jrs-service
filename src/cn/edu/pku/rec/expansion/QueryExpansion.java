package cn.edu.pku.rec.expansion;

import java.util.ArrayList;

/**
 * @author Lan Zheng
 * @version 1.0
 * */

public interface QueryExpansion {
	
	public ArrayList <String> similarWords (String input, int num);
}