package cn.edu.pku.rec.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Position;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.util.FileInput;
import cn.edu.pku.util.FilePath;
import cn.edu.pku.util.HanLPSegmenter;
/**
 * 文本预处理
 * @author Lan Zheng
 * @version 1.0
 * */
public class PreProcessor {
	
	/** 分词器 */
	public static CRFClassifier<CoreLabel> segmenter;
	/** 停用词、停用符号文件 */
	public static String stopwordsFile = FilePath.nlpPath+"stopwords.txt";
	/** 停用词、停用符号表 */
	public static HashSet <String> stopwords = new HashSet <String> ();
	/** 输入文件编码方式 */
	public static final String encodingInput = "UTF-8";
	/** 输出文件编码方式 */
	public static final String encodingOutput = "UTF-8";
	/** 停用符号 */
	public static final String STOPSIGNS = "[\\p{P}~$`^=|<>～｀＄＾＋＝｜＜＞￥× \\s|\t|\r|\n]+";
		
	/**
	 * 加载分词器
	 * */
	public static void loadSegmenter()
	{	
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", FilePath.standfordPath);
		// props.setProperty("NormalizationTable", "data/norm.simp.utf8");
		//props.setProperty("normTableEncoding", "UTF-8");
		// below is needed because CTBSegDocumentIteratorFactory accesses it
		props.setProperty("serDictionary",FilePath.standfordPath+"/dict-chris6.ser.gz");
		
		//props.setProperty("testFile", args[0]);
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");

		segmenter = new CRFClassifier<CoreLabel>(props);
		segmenter.loadClassifierNoExceptions(FilePath.standfordPath+"/ctb.gz", props);
		//segmenter.classifyAndWriteAnswers(args[0]);
		//System.out.println(segmenter.classifyToString("今天天气不错啊"));
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
	 * 加载停用词、停用符号表
	 * @param stopwordsFilePath 停用词表文件路径
	 * @throws IOException 找不到停用词、停用符号文件
	 */
	public static void loadStopWords(String stopwordsFilePath) throws IOException
	{
		if(stopwordsFilePath != null)
			stopwordsFile = stopwordsFilePath;
		InputStreamReader isr = new InputStreamReader(new FileInputStream(stopwordsFile));
		BufferedReader reader = new BufferedReader(isr);
		String line = null;
		while((line = reader.readLine()) != null)
			stopwords.add(new String(line.trim()));
		stopwords.add(new String("\r"));
		stopwords.add(new String("\n"));
		stopwords.add(new String("\r\n"));
		stopwords.add(new String("\n\r"));
//		for(Iterator it = stopwords.iterator();it.hasNext();)
//			System.out.println(it.next());
		reader.close();
	}	

	/** 
	 * 停用词、停用符号判断
	 * @param token 待识别的词素
	 * @return true表示该词素为停用词或停用符号，false表示该词素不是停用词或停用符号
	 */
	public static boolean isStopWords(String token)
	{
		if(stopwords.contains(token))
			return true;
		return false;
	}

	/** 
	 * 分词操作，保留c++，.net，c#，sqlserver等特殊词素
	 * @param textContent 待分词的文本
	 * @return 分词后的词素数组
	 */
	public static String [] stopWordExceptCPP(String textContent)
	{
		textContent = textContent.replaceAll("c#", "+++++");
		textContent = textContent.replaceAll("C#", "+++++");
		textContent = textContent.replaceAll(STOPSIGNS, " ");
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
		textContent = segmenter.classifyToString(textContent);
		textContent = textContent.replaceAll("c #", "c#");
		return textContent.split(" +");
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
	 * 禁用 对HTML文件进行分词，只用于智联招聘
	 * @param htmlPath HTML文件路径
	 * @param outputPath 输出文件路径
	 * @throws IOException 找不到HTML文件

	public static void dealWithZhilianHtml(String htmlPath, String outputPath) throws IOException
	{
		FileOutputStream t1 = new FileOutputStream(new File(outputPath));
		OutputStreamWriter t2 = new OutputStreamWriter(t1, encodingOutput);
		BufferedWriter t3 = new BufferedWriter(t2);
			
		File Html = new File(htmlPath);
		Document doc = Jsoup.parse(Html, encodingInput);
		
		//获取标题
		Element script = doc.select("script").first();
		String [] scriptContext = script.html().split("'");
		//职位的索引为5
		if(scriptContext.length >= 6)
			t3.write(scriptContext[5].toLowerCase());
		t3.newLine();
		
		//对标签div进行解析
		Elements divs = doc.select("div");
		for(Element div : divs)
		{
//			System.out.println(div.html());
			if(!div.attr("class").equals("terminalpage-content"))
				continue;
			if(div.text().equals(""))
				continue;
					
			String [] tokens = stopWordExceptCPP(div.text().trim());

			Pattern p;
			for(int i = 0; i < tokens.length; i ++)
			{
				//如果不包含大写字母返回原值，否则返回小写形式
				tokens[i] = lowerCase(tokens[i].trim());
					
				//去除c以外的其他单个字母
				p = Pattern.compile("[a-b]|[d-z]");
				if(tokens[i].length() == 1
						&& p.matcher(tokens[i]).find())
					continue;
				
				//去除完全是数字的词
				if(tokens[i].matches("[0-9]+"))
					continue;
				
				tokens[i] = tokens[i].replaceAll("\r", "");
				tokens[i] = tokens[i].replaceAll("\n", "");
				tokens[i] = tokens[i].replaceAll("\t", "");
				//去除停用词和网址等特殊词
				if(isStopWords(tokens[i]) || tokens[i].length() == 0
//						|| tokens[i].contains("\n")
//						|| tokens[i].contains("\r")
						|| tokens[i].contains("-")
						|| tokens[i].contains("@")
						|| tokens[i].contains("COM")
						|| tokens[i].contains("com")
						|| tokens[i].contains("CN")
						|| tokens[i].contains("cn")
						|| tokens[i].contains("WWW")
						|| tokens[i].contains("www"))
					continue;
				t3.write(tokens[i] + " ");
			}
			t3.close();
			break;
		}
	}
*/
	/** 
	 * 对文本文件进行分词
	 * @param textPath 文本文件路径
	 * @param outputPath 输出文件路径
	 * @throws IOException 找不到文本文件
	 */
	public static void dealWithText(String textPath, String outputPath) throws IOException
	{
		FileInput fi = new FileInput(textPath);
		String content = new String();
		String line = new String ();
		try {
			while ((line = fi.reader.readLine()) != null) {
				content += line.trim();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
		
		dealWithString(content, outputPath);
	}
	
	/** 
	 * 对String类型的Resume进行分词，用于对String型的文件进行处理
	 * @param input Resume的String型文件路径
	 * @param outputPath 输出文件路径
	 * @throws IOException 找不到String文件
	 */
	public static void dealWithString(String input, String outputPath) throws IOException
	{
		HanLPSegmenter.segmentation(input.trim(), true, true, outputPath);
	}
	
	/** 
	 * 批处理，只用于智联招聘
	 * @param srcDir 源文件路径
	 * @param newDir 目标写入文件路径

	public static void batchDealWithZhilianHtml(String srcDir, String newDir)
	{
		File [] files = new File(srcDir).listFiles();
		for(int i = 0; i < files.length; i ++)
		{
			if(files[i].isDirectory())
			{
				String makeNewDir = newDir + "\\" + files[i].getName() + "_pro";
				new File(makeNewDir).mkdirs();
				try {
					batchDealWithZhilianHtml(files[i].getAbsolutePath(), makeNewDir);
					}catch(Exception e){}
			}
			else
			{
				try {
					dealWithZhilianHtml(files[i].getAbsolutePath(), newDir + "\\" + files[i].getName() + ".txt");
					System.out.println(newDir + "\\" + files[i].getName() + ".txt" + " finished");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
*/
	
	/** 
	 * 对Resume类进行分词，用于对Resume类的文件进行处理
	 * @param input Resume类文件路径
	 * @param outputPath 输出文件路径
	 * @throws IOException 找不到HTML文件
	 */
	public static void dealWithResume(Resume input, List<Education> edulist,
			List<WorkExperience> worklist, String outputPath) {
		String content = new String();
		content += input.getSpeciality();
		content += input.getOtherInfo();
		content += input.getWorkingPlace();
		for(Education edu : edulist)
		{
			content += edu.getAcademy();
			content += edu.getDegree();
			content += edu.getSchool();
			content += edu.getMajor();
		}
		for(WorkExperience work : worklist)
		{
			content += work.getCompany();
			content += work.getJobTitle();
			content += work.getDescription();
		}
		content += input.getIndustryIntension();
		content += input.getCategoryIntension();
		
		HanLPSegmenter.segmentation(content.trim(), true, true, outputPath);
	}
	
	/** 
	 * 对Position类进行分词，用于对Position类的文件进行处理
	 * @param input Position类文件路径
	 * @param outputPath 输出文件路径
	 * @throws IOException 找不到HTML文件
	 */
	public static void dealWithPosition(Position input, String outputPath) throws IOException
	{
		String content = input.getPosTitle() + "\n" + input.getPosDescription();
		
		HanLPSegmenter.segmentation(content.trim(), true, true, outputPath);
	}
	
	public static void main(String args[]) throws IOException
	{
//		SetPath path = new SetPath();
//		path.setSighanCorporaDict("");
//		path.setSerDictionary("");
//		path.setLoadDictionary("");
//		loadSegmenter(path);
//		loadStopWords(null);
//		
//		new File("E:\\workj2ee\\智联招聘 训练集_pro").mkdirs();
//		batchDealWithZhilianHtml("E:\\workj2ee\\智联招聘 训练集", "E:\\workj2ee\\智联招聘 训练集_pro");
	}
}
