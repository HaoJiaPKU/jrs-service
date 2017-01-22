package cn.edu.pku.search.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chenlb.mmseg4j.analysis.SimpleAnalyzer;

import cn.edu.pku.rec.classifier.Classifier;
import cn.edu.pku.rec.comparer.Comparer;
import cn.edu.pku.rec.knowledge.KnowledgeBase;
import cn.edu.pku.rec.processor.PositionInfo;
import cn.edu.pku.rec.processor.PreProcessor;
import cn.edu.pku.rec.processor.ResumeInfo;
import cn.edu.pku.search.dao.PositionDAO;
import cn.edu.pku.search.dao.PositionTagDAO;
import cn.edu.pku.search.dao.RelevanceDAO;
import cn.edu.pku.search.dao.ResumeDAO;
import cn.edu.pku.search.domain.AbstractPosition;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.MatchPosition;
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Pager;
import cn.edu.pku.search.domain.PositionJobpopo;
import cn.edu.pku.search.domain.Position;
import cn.edu.pku.search.domain.PositionTag;
import cn.edu.pku.search.domain.Relevance;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.Resume51Job;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.user.dao.EmployeeDAO;
import cn.edu.pku.user.dao.EmployeeTagDAO;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.util.FilePath;
import cn.edu.pku.util.HanLPSegmenter;
import cn.edu.pku.util.SystemContext;

@Service
public class SearchServiceImpl implements SearchService {

	public static final Logger logger = Logger.getLogger(SearchServiceImpl.class);
	
	ResumeDAO resumeDAO;
	PositionDAO positionDAO;
	PositionTagDAO positionTagDAO;
	RelevanceDAO relevanceDAO;
	EmployeeDAO employeeDAO;
	EmployeeTagDAO employeeTagDAO;

	public ResumeDAO getResumeDAO() {
		return resumeDAO;
	}

	@Resource
	public void setResumeDAO(ResumeDAO resumeDAO) {
		this.resumeDAO = resumeDAO;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	@Resource
	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public RelevanceDAO getRelevanceDAO() {
		return relevanceDAO;
	}

	@Resource
	public void setRelevanceDAO(RelevanceDAO relevanceDAO) {
		this.relevanceDAO = relevanceDAO;
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	@Resource
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public EmployeeTagDAO getEmployeeTagDAO() {
		return employeeTagDAO;
	}

	@Resource
	public void setEmployeeTagDAO(EmployeeTagDAO employeeTagDAO) {
		this.employeeTagDAO = employeeTagDAO;
	}

	public PositionTagDAO getPositionTagDAO() {
		return positionTagDAO;
	}

	@Resource
	public void setPositionTagDAO(PositionTagDAO positionTagDAO) {
		this.positionTagDAO = positionTagDAO;
	}

	public Pager<AbstractPosition> searchPosition(String field, String queryString,
			int offset) {
//		KnowledgeBase.setSkillFile(100, FilePath.nlpPath+"skillList top100.txt");
//		KnowledgeBase.setSimilFile(FilePath.nlpPath+"similarity matrix.txt");
//		try {
//			KnowledgeBase.loadKnowledgeBase();
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		QueryExpansion wqe = new WordQueryExpansion ();
//		List<String> queryStringList = wqe.similarWords(queryString, 5);
//		for(String str : queryStringList) {
//			System.out.println(str);
//		}
		String index = FilePath.positionIndex;

		Pager<AbstractPosition> res = null; 
		
		try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)))){
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new SimpleAnalyzer();
	
			QueryParser parser = new QueryParser(field, analyzer);
	
			String line = queryString;
			
			line = line.trim();
	
			Query query = parser.parse(line);
			logger.info("Searching for: " + query.toString(field));
	
			res = doPagingSearchPosition(searcher, query, offset);
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	@Override
	public Pager<AbstractResume> searchResume(String field, String queryString,
			int offset) {
		String index = FilePath.resumeIndex;

		Pager<AbstractResume> res = null; 
		
		try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)))){
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new SimpleAnalyzer();
	
			QueryParser parser = new QueryParser(field, analyzer);
	
			String line = queryString;
			
			line = line.trim();
	
			Query query = parser.parse(line);
			System.out.println("Searching for: " + query.toString(field));
	
			res = doPagingSearchResume(searcher, query, offset);
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	@Override
	public void updateRelevanceForEmployee(long employeeId) {
		Resume resume = resumeDAO.getResume(employeeId);
		List<Education> eduList = resumeDAO.listEducation(employeeId);
		List<WorkExperience> workList = resumeDAO.listWorkExperience(employeeId);
		
		try {

//			PreProcessor.loadSegmenter();
//			PreProcessor.loadStopWords(null);
			HanLPSegmenter.loadStopword(null);
			PreProcessor.dealWithResume(resume, eduList, workList, FilePath.nlpPath + "tmp/resume.txt");
			
			KnowledgeBase.setPositionFile(100, FilePath.nlpPath + "positionList top100.txt");
			KnowledgeBase.setSkillFile(100, FilePath.nlpPath + "skillList top100.txt");
			KnowledgeBase.setProbFile(FilePath.nlpPath + "contribution matrix.txt");
			KnowledgeBase.setSimilFile(FilePath.nlpPath + "similarity matrix.txt");
			KnowledgeBase.loadKnowledgeBase();

			Classifier.setProbFile(15, 40, FilePath.nlpPath + "classifier param.txt");
			Classifier.loadModel();
			Classifier classifier = new Classifier();

			int updateSize = 1000;
			double [] distribution;

			logger.info("简历文件分析中间结果*************************");
			ResumeInfo resumeInfo = new ResumeInfo();
			resumeInfo.process(FilePath.nlpPath + "tmp/resume.txt");
			for (int i = 0; i < resumeInfo.skillVector.length; i++) {
				if (resumeInfo.skillVector[i] > 0) {
					logger.info(KnowledgeBase.skillList[i] + " "
							+ resumeInfo.skillVector[i] + "\n");
				}
			}
			distribution = classifier.getDistri(resumeInfo.skillVector);
			for (int i = 0; i < distribution.length; i++) {
				logger.info(KnowledgeBase.positionList[i] + "	"
						+ distribution[i]);
			}
			
			// TODO hasTag is supposed to be boolean
			// TODO 标签的更新不应该放在这个位置，考虑保存简历时
			// TODO 更新逻辑依然有错误
			Employee employee = employeeDAO.load(employeeId);
			if (employee.getHasTag() != -1) {
				employeeTagDAO.deleteEmployeeTag(employeeId);
				for (int i = 0; i < distribution.length; i ++) {
					EmployeeTag employeeTag = new EmployeeTag(
							employeeId,
							KnowledgeBase.positionList[i],
							distribution[i]);
					employeeTagDAO.add(employeeTag);
//					System.out.println("add employeeTag");
				}
				employee.setHasTag(1);
				employeeDAO.update(employee);
			}
			
			relevanceDAO.deleteRelevanceByEmployeeId(employeeId);
			
			
			for(int i = 0; ;i ++) {
				List<Position> positionList = positionDAO
						.listPositionBBS(i * updateSize, updateSize);
				//只计算一页
				if (i == 1) {
					break;
				}
				System.out.println(String.valueOf(i * updateSize) + " data from bbs start");
				
				if(positionList == null || positionList.size() == 0)
					break;
				
				for (Position positionBBS : positionList) {
					PreProcessor.dealWithString(positionBBS.textField(), FilePath.nlpPath+ "tmp/position.txt");
					
					logger.info("职位文件分析中间结果*************************");
					PositionInfo positionInfo = new PositionInfo();
					positionInfo.process(FilePath.nlpPath + "tmp/position.txt");
					for (int j = 0; j < positionInfo.skillVector.length; j++) {
						if (positionInfo.skillVector[j] > 0) {		
							logger.info(KnowledgeBase.skillList[j] + " "
									+ positionInfo.skillVector[j] + "\n");
						}
					}
					distribution = classifier.getDistri(positionInfo.skillVector);
					for (int j = 0; j < distribution.length; j++) {
						logger.info(KnowledgeBase.positionList[j] + "	"
								+ distribution[j]);
					}
					
					// TODO hasTag is supposed to be boolean
					// TODO 标签的更新不应该放在这个位置，考虑保存招聘信息时
					if (positionBBS.getHasTag() != -1) {
						positionTagDAO.deletePositionTag(positionBBS.getId());
						for (int j = 0; j < distribution.length; j ++) {
							PositionTag positionTag = new PositionTag(
									positionBBS.getId(),
									KnowledgeBase.positionList[j],
									distribution[j]);
							positionTagDAO.add(positionTag);
						}
						positionBBS.setHasTag(1);
						positionDAO.updateBBS(positionBBS);
					}
	
					logger.info("职位与简历匹配度计算*************************");
					Comparer comparer = new Comparer();
	
					double rel = comparer.compare(positionInfo, resumeInfo);
					Relevance relevance = new Relevance(employeeId, 0, 2, positionBBS.getId(), rel);
					relevanceDAO.update(relevance);
					logger.info(rel);
					
				}
				System.out.println(String.valueOf(i * updateSize) + " data from bbs end");
			}
			
			for(int i = 0; ;i ++) {
				List<PositionJobpopo> positionList = positionDAO
						.listPosition(i * updateSize, updateSize);
				//只计算一页
				if (i == 1) {
					break;
				}
				System.out.println(String.valueOf(i * updateSize) + " data from jobpopo start");
				
				if(positionList == null || positionList.size() == 0)
					break;
				
				for (PositionJobpopo position : positionList) {
					PreProcessor.dealWithString(position.textField(), FilePath.nlpPath+ "tmp/position.txt");
	
					logger.info("职位文件分析中间结果*************************");
					PositionInfo positionInfo = new PositionInfo();
					positionInfo.process(FilePath.nlpPath + "tmp/position.txt");
					for (int j = 0; j < positionInfo.skillVector.length; j++) {
						if (positionInfo.skillVector[j] > 0) {		
							logger.info(KnowledgeBase.skillList[j] + " "
									+ positionInfo.skillVector[j] + "\n");
						}
					}
					distribution = classifier.getDistri(positionInfo.skillVector);
					for (int j = 0; j < distribution.length; j++) {
						logger.info(KnowledgeBase.positionList[j] + "	"
								+ distribution[j]);
					}
	
					logger.info("职位与简历匹配度计算*************************");
					Comparer comparer = new Comparer();
	
					double rel = comparer.compare(positionInfo, resumeInfo);
					Relevance relevance = new Relevance(employeeId, 0, 1, position.getId(), rel);
					relevanceDAO.update(relevance);
					logger.info(rel);
	
				}
				System.out.println(String.valueOf(i * updateSize) + " data from bbs end");
			}
			
			System.out.println("info : update for employee complete");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Transactional
	@Override
	public void updateRelevanceForEmployer(long positionId) {
		PositionJobpopo position = positionDAO.loadPosition(positionId);
		
		try {

//			PreProcessor.loadSegmenter();
//			PreProcessor.loadStopWords(null);
			HanLPSegmenter.loadStopword(null);
			PreProcessor.dealWithString(position.textField(), FilePath.nlpPath + "tmp/position.txt");
			
			KnowledgeBase.setPositionFile(100, FilePath.nlpPath + "positionList top100.txt");
			KnowledgeBase.setSkillFile(100, FilePath.nlpPath + "skillList top100.txt");
			KnowledgeBase.setProbFile(FilePath.nlpPath + "contribution matrix.txt");
			KnowledgeBase.setSimilFile(FilePath.nlpPath + "similarity matrix.txt");
			KnowledgeBase.loadKnowledgeBase();

			Classifier.setProbFile(15, 40, FilePath.nlpPath + "classifier param.txt");
			Classifier.loadModel();
			Classifier classifier = new Classifier();

			double[] distribution;

			logger.info("职位文件分析中间结果*************************");
			PositionInfo positionInfo = new PositionInfo();
			positionInfo.process(FilePath.nlpPath + "tmp/position.txt");
			for (int i = 0; i < positionInfo.skillVector.length; i++) {
				if (positionInfo.skillVector[i] > 0) {		
					logger.info(KnowledgeBase.skillList[i] + " "
							+ positionInfo.skillVector[i] + "\n");
				}
			}
			distribution = classifier.getDistri(positionInfo.skillVector);
			for (int i = 0; i < distribution.length; i++) {
				logger.info(KnowledgeBase.positionList[i] + "	"
						+ distribution[i]);
			}
			
			
			for(int i = 0;;i++) {
				List<Resume> resumeList = resumeDAO.listResume(i*100, 100);
				if(resumeList == null || resumeList.size() == 0)
					break;
				for (Resume resume : resumeList) {
					List<Education> eduList = resumeDAO.listEducation(resume.getEmployeeId());
					List<WorkExperience> workList = resumeDAO.listWorkExperience(resume.getEmployeeId());
					PreProcessor.dealWithResume(resume, eduList, workList,FilePath.nlpPath + "tmp/resume.txt");

					logger.info("简历文件分析中间结果*************************");
					ResumeInfo resumeInfo = new ResumeInfo();
					resumeInfo.process(FilePath.nlpPath + "tmp/resume.txt");
					for (int j = 0; j < resumeInfo.skillVector.length; j++) {
						if (resumeInfo.skillVector[j] > 0) {
							logger.info(KnowledgeBase.skillList[j] + " "
									+ resumeInfo.skillVector[j] + "\n");
						}
					}
					distribution = classifier.getDistri(resumeInfo.skillVector);
					for (int j = 0; j < distribution.length; j++) {
						logger.info(KnowledgeBase.positionList[j] + "	"
								+ distribution[j]);
					}
	
					logger.info("职位与简历匹配度计算*************************");
					Comparer comparer = new Comparer();
	
					double rel = comparer.compare(positionInfo, resumeInfo);
					Relevance relevance = new Relevance(resume.getEmployeeId(),0,1,
							positionId, rel);
					relevanceDAO.update(relevance);
					logger.info(rel);
				}
			}
			
			for(int i = 0;;i++) {
				List<Resume51Job> resumeList = resumeDAO.listResume51Job(i*100, 100);
				if(resumeList == null || resumeList.size() == 0)
					break;
				for (Resume51Job resume : resumeList) {
					PreProcessor.dealWithText("../webapps/"+resume.getPath(),FilePath.nlpPath + "tmp/resume.txt");

					logger.info("简历文件分析中间结果*************************");
					ResumeInfo resumeInfo = new ResumeInfo();
					resumeInfo.process(FilePath.nlpPath + "tmp/resume.txt");
					for (int j = 0; j < resumeInfo.skillVector.length; j++) {
						if (resumeInfo.skillVector[j] > 0) {
							logger.info(KnowledgeBase.skillList[j] + " "
									+ resumeInfo.skillVector[j] + "\n");
						}
					}
					distribution = classifier.getDistri(resumeInfo.skillVector);
					for (int j = 0; j < distribution.length; j++) {
						logger.info(KnowledgeBase.positionList[j] + "	"
								+ distribution[j]);
					}
	
					logger.info("职位与简历匹配度计算*************************");
					Comparer comparer = new Comparer();
	
					double rel = comparer.compare(positionInfo, resumeInfo);
					Relevance relevance = new Relevance(0,resume.getId(),1,
							positionId, rel);
					relevanceDAO.update(relevance);
					logger.info(rel);
				}
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	@Transactional
	@Override
	public Pager<MatchPosition> listMatchPosition(long employeeId,
			int offset) {
		List<Relevance> list = relevanceDAO.listRelevanceForEmployee(employeeId, offset);
		List<MatchPosition> matchList = new ArrayList<>();
		for(Relevance rel : list) {
			if(rel.getPositionSource() == 1) {
				PositionJobpopo position = positionDAO.loadPosition(rel.getPositionId());
				List<PositionTag> positionTagList = positionTagDAO.listPositionTag(rel.getPositionId());
				if (position.getDescription().length() > 100) {
					position.setDescription(position.getDescription().substring(0, 100) + "...");//为了前台只显示两三行内容
				} else {
					position.setDescription(position.getDescription() + "...");//为了前台只显示两三行内容
				}
				MatchPosition match = new MatchPosition(employeeId, rel.getRelevance(), position, positionTagList);
				matchList.add(match);
			} else if(rel.getPositionSource() == 2) {
				Position positionBBS = positionDAO.loadPositionBbs(rel.getPositionId());
				List<PositionTag> positionTagList = positionTagDAO.listPositionTag(rel.getPositionId());
				MatchPosition match = new MatchPosition(employeeId, rel.getRelevance(), positionBBS, positionTagList);
				matchList.add(match);
			}
		}
		Pager<MatchPosition> relevancePager = new Pager<>();
		relevancePager.setDatas(matchList);
		relevancePager.setOffset(offset);
		relevancePager.setSize(SystemContext.getSize());
		relevancePager.setTotal(relevanceDAO.getPositionNumber(employeeId));
		return relevancePager;
	}
	
	@Transactional
	@Override
	public Pager<MatchResume> listMatchResume(long positionId, int offset) {
		List<Relevance> list = relevanceDAO.listRelevanceForEmployer(positionId, offset);
		List<MatchResume> matchList = new ArrayList<>();
		for(Relevance rel : list) {
			if(rel.getEmployeeId() == 0) {
				Resume51Job resume = resumeDAO.getResume51Job(rel.getResumeId());
				MatchResume match = new MatchResume(positionId, rel.getRelevance(), resume);
				matchList.add(match);
			} else {
				Resume resume = resumeDAO.getResume(rel.getEmployeeId());
				MatchResume match = new MatchResume(positionId, rel.getRelevance(), resume);
				matchList.add(match);
			}
		}
		Pager<MatchResume> relevancePager = new Pager<>();
		relevancePager.setDatas(matchList);
		relevancePager.setOffset(offset);
		relevancePager.setSize(SystemContext.getSize());
		relevancePager.setTotal(relevanceDAO.getResumeNumber(positionId));
		return relevancePager;
	}
	
	private Pager<AbstractPosition> doPagingSearchPosition(IndexSearcher searcher,
			Query query, int offset)
			throws IOException {

		Pager<AbstractPosition> res = new Pager<>();
		List<AbstractPosition> list = new ArrayList<>();
		int hitsPerPage = SystemContext.getSize();

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		logger.info(numTotalHits + " total matching documents");
		
		int end = Math.min(numTotalHits, offset + hitsPerPage);

		if (end > hits.length) {
			// the 5 pages to show is not enough
			hits = searcher.search(query, numTotalHits).scoreDocs;
		}

		end = Math.min(hits.length, offset + hitsPerPage);

		for (int i = offset; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			long id = Long.parseLong(doc.get("id"));
			String source = doc.get("source");
			if(source.equals("jobpopo")) {
				PositionJobpopo position = positionDAO.loadPosition(id);
				if(position.getDescription().length()>100) {
					position.setDescription(position.getDescription().substring(0, 100)+"...");
				}
				list.add(position);
			} else {
				Position position = positionDAO.loadPositionBbs(id);
				list.add(position);
			}
		}
		res.setDatas(list);
		res.setTotal(numTotalHits);
		res.setOffset(offset);
		res.setSize(hitsPerPage);
		return res;
	}

	private Pager<AbstractResume> doPagingSearchResume(IndexSearcher searcher,
			Query query, int offset)
			throws IOException {

		Pager<AbstractResume> res = new Pager<>();
		List<AbstractResume> list = new ArrayList<>();
		int hitsPerPage = SystemContext.getSize();

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		int end = Math.min(numTotalHits, offset + hitsPerPage);

		if (end > hits.length) {
			// the 5 pages to show is not enough
			hits = searcher.search(query, numTotalHits).scoreDocs;
		}

		end = Math.min(hits.length, offset + hitsPerPage);

		for (int i = offset; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String source = doc.get("source");
			if(source.equals("51job")) {
				Resume51Job resume = new Resume51Job();
				resume.setPath(doc.get("path"));
				resume.setContent(doc.get("contents"));
				list.add(resume);
			} else {
				Resume resume = resumeDAO.getResume(Long.parseLong(doc.get("path")));
				list.add(resume);
			}
			
		}
		res.setDatas(list);
		res.setTotal(numTotalHits);
		res.setOffset(offset);
		res.setSize(hitsPerPage);
		return res;
	}

	
}
