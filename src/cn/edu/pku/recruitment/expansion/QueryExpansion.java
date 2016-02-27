package cn.edu.pku.recruitment.expansion;

import java.util.ArrayList;

/**
 * @author lzh@pku
 * @version 1.0
 * */

public interface QueryExpansion {
	
	public ArrayList <String> similarWords (String input, int num);
}