/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/7 20:14</create-date>
 *
 * <copyright file="DemoPosTagging.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package cn.edu.pku.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import cn.edu.pku.util.FileInput;
import cn.edu.pku.util.FileOutput;

/**
 * 词性标注
 * @author hankcs
 */
public class HanLPSegmenter
{
	/** 停用词、停用符号表 */
	public static HashSet<String> stopword = new HashSet<String> ();
	/** 停用词、停用符号文件 */
	public static String stopwordsFile = FilePath.nlpPath + "stopwords.txt";
	/** 停用符号 */
	public static final String StopSigns = "[\\p{P}~$`^=|<>～｀＄＾＋＝｜＜＞￥× \\s|\t|\r|\n]+";
	/** 停用模式 */
	public static Pattern pattern = Pattern.compile("[a-b]|[d-z]");
	
	/** 
	 * 加载停用词、停用符号表
	 * @param stopwordsFilePath 停用词表文件路径
	 * @throws IOException 找不到停用词、停用符号文件
	 */
	public static void loadStopword(String _stopwordsFile) {
		if(_stopwordsFile != null)
			stopwordsFile = _stopwordsFile;
		stopword.clear();
		FileInput fi = new FileInput(stopwordsFile);
		String line = new String ();
		try {
			while ((line = fi.reader.readLine()) != null) {
				stopword.add(line.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
	}
	
	/** 
	 * 设置停用词、停用符号文件路径 
	 * @param _matchProbFilePath 停用词、停用符号文件路径
	 * */
	public static void setStopWordsFile(String _stopwordsFile)
	{
		stopwordsFile = _stopwordsFile;
	}
	
	/** 
	 * 停用词、停用符号判断
	 * @param token 待识别的词素
	 * @return true表示该词素为停用词或停用符号，false表示该词素不是停用词或停用符号
	 */
	public static boolean isStopWords(String token)
	{
		if(stopword.contains(token))
			return true;
		return false;
	}
	
	/** 
	 * 将字母的大写形式转化为小写形式，如果不包含大写字母返回原值，否则返回小写形式
	 * @param token 待转化的词素
	 * @return 转化后的词素
	 */
	public static String lowerCase(String token)
	{
		if(Pattern.compile("(?i)[A-Z]").matcher(token).find())
			return token.toLowerCase();
		return token;
	}
	
	/** 
	 * 去除停用词预处理，保留c++，.net，c#，sqlserver等特殊词素
	 * @param textContent 待分词的文本
	 * @return 处理后的文本
	 */
	public static String preStopWord(String textContent)
	{
		textContent = textContent.replaceAll("c#", "+++++");
		textContent = textContent.replaceAll("C#", "+++++");
		textContent = textContent.replaceAll(StopSigns, " ");
		textContent = textContent.replaceAll("[+][+][+][+][+]", "c# ");
		textContent = textContent.replaceAll("c[+][+]", "@pattern1@");
		textContent = textContent.replaceAll("C[+][+]", "@pattern1@");
		textContent = textContent.replaceAll(".net", "@pattern2@");
		textContent = textContent.replaceAll(".Net", "@pattern2@");
		textContent = textContent.replaceAll(".NET", "@pattern2@");
		textContent = textContent.replaceAll("div[+]css", "@pattern3@");
		textContent = textContent.replaceAll("css[+]div", "@pattern3@");
		textContent = textContent.replaceAll("DIV[+]CSS", "@pattern3@");
		textContent = textContent.replaceAll("CSS[+]DIV", "@pattern3@");
		textContent = textContent.replaceAll("Div[+]Css", "@pattern3@");
		textContent = textContent.replaceAll("Css[+]Div", "@pattern3@");
		textContent = textContent.replaceAll("[+]", " ");
		textContent = textContent.replaceAll("@pattern1@", "c++ ");
		textContent = textContent.replaceAll("@pattern2@", ".net");
		textContent = textContent.replaceAll("@pattern3@", "div+css");
		textContent = textContent.replaceAll("sql server", "sqlserver");
		textContent = textContent.replaceAll("Sql server", "sqlserver");
		textContent = textContent.replaceAll("Sql Server", "sqlserver");
		textContent = textContent.replaceAll("sql Server", "sqlserver");
		textContent = textContent.replaceAll("SQL server", "sqlserver");
		textContent = textContent.replaceAll("SQL Server", "sqlserver");
		textContent = textContent.replaceAll("c #", "c#");
		return textContent;
	}
	
	/**
	 * 对token进行处理
	 * 如果不包含大写字母返回原值，否则返回小写形式
	 * 去除c以外的其他单个字母和汉字
	 * 去除完全是数字的词
	 * 去除停用词和网址等特殊词
	 * @param token
	 * @return 如果出现以上各种情况，返回null，否则返回处理后的token
	 * */
	public static String normalizedToken(String token) {
		//如果不包含大写字母返回原值，否则返回小写形式
		token = lowerCase(token.trim());
			
		//去除c以外的其他单个字母和汉字
		if(token.length() == 1 && !token.equals("c")) {
			return null;
		}
		
		//去除完全是数字的词
		if(token.matches("[0-9]+")) {
			return null;
		}
		
		if (token.equals("ee")) {
			token = "j2ee";
		}
		token = token.replaceAll("\r", "");
		token = token.replaceAll("\n", "");
		token = token.replaceAll("\t", "");
		token = token.trim();
		
		//去除停用词和网址等特殊词
		if(isStopWords(token)
				|| token.length() == 0
				|| token.contains("-")
				|| token.contains("@")
				|| token.contains("COM")
				|| token.contains("com")
				|| token.contains("CN")
				|| token.contains("cn")
				|| token.contains("WWW")
				|| token.contains("www")) {
			return null;
		}
		
		if (token.equals("职位")
				|| token.equals("职业")
				|| token.equals("岗位")
				|| token.equals("职责")
				|| token.equals("描述")
				|| token.equals("要求")
				|| token.equals("专业")
				|| token.equals("毕业生")
				|| token.equals("学历")
				|| token.equals("本科")
				|| token.equals("专科")
				|| token.equals("能力")				
				) {
			return null;
		}
		return token;
	}	
	
	/**
	 * 判断是否为给定词性
	 * @param token
	 * */
	public static boolean isGivenPos(String token) {
		return token.equals("gi")
				|| token.equals("n")
				|| token.equals("ng")
				|| token.equals("nx");
	}
	
	/** 对文本进行分词，返回分词后的数组
	 * @param input 输入字符串
	 * @param isNormalized 是否需要归一化
	 * @param isSave 是否要将结果保存到文件
	 * @return 分好词的数组
	 * */
	public static String [] segmentation(
			String input, boolean isNormalized, boolean isSave, String outputPath) {
		
		FileOutput fo = new FileOutput();
		if (isSave) {
			fo = new FileOutput(outputPath);
		}
		Segment segment = HanLP.newSegment();
		segment.enablePartOfSpeechTagging(true);
		
		input = preStopWord(input.trim());
		List<Term> termList = segment.seg(input);
		
		String [] temp = new String[termList.size()];
		int index = 0;
		Iterator it = termList.iterator();
		while (it.hasNext()) {
			String [] term = it.next().toString().split("/");
			if (isNormalized) {
				term[0] = normalizedToken(term[0]);
				if (term[0] == null
						|| term[0].length() == 0
						|| term[0].equals("")) {
					continue;
				}
			}
			temp[index ++] = term[0];
		}
		
		String [] ret = new String[index];
		for (int i = 0; i < index; i ++) {
			ret[i] = temp[i];
			if (isSave) {
				try {
					fo.t3.write(ret[i] + " ");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (isSave) {
			fo.closeOutput();
		}
		
		return ret;
	}
	
    public static void main(String[] args) {}
}
