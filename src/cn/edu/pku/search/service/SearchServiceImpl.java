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

import cn.edu.pku.recruitment.classifier.Classifier;
import cn.edu.pku.recruitment.comparer.Comparer;
import cn.edu.pku.recruitment.knowledgeBase.KnowledgeBase;
import cn.edu.pku.recruitment.positionProcessor.PositionInfo;
import cn.edu.pku.recruitment.preProcessor.PreProcessor;
import cn.edu.pku.recruitment.resumeProcessor.ResumeInfo;
import cn.edu.pku.search.dao.RecruitmentDAO;
import cn.edu.pku.search.dao.RelevanceDAO;
import cn.edu.pku.search.dao.ResumeDAO;
import cn.edu.pku.search.domain.AbstractRecruitment;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.MatchRecruitment;
import cn.edu.pku.search.domain.Pager;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.RecruitmentBBS;
import cn.edu.pku.search.domain.Relevance;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.Resume51Job;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.util.FilePath;
import cn.edu.pku.util.SystemContext;

@Service
public class SearchServiceImpl implements SearchService {

	public static final Logger logger = Logger.getLogger(SearchServiceImpl.class);
	
	ResumeDAO resumeDao;
	RecruitmentDAO recruitmentDao;
	RelevanceDAO relevanceDao;


	public ResumeDAO getResumeDao() {
		return resumeDao;
	}

	@Resource
	public void setResumeDao(ResumeDAO resumeDao) {
		this.resumeDao = resumeDao;
	}

	public RecruitmentDAO getRecruitmentDao() {
		return recruitmentDao;
	}

	@Resource
	public void setRecruitmentDao(RecruitmentDAO recruitmentDao) {
		this.recruitmentDao = recruitmentDao;
	}

	public RelevanceDAO getRelevanceDao() {
		return relevanceDao;
	}

	@Resource
	public void setRelevanceDao(RelevanceDAO relevanceDao) {
		this.relevanceDao = relevanceDao;
	}

	public Pager<AbstractRecruitment> searchRecruitment(String field, String queryString,
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
		String index = "index";

		Pager<AbstractRecruitment> res = null; 
		
		try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)))){
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new SimpleAnalyzer();
	
			QueryParser parser = new QueryParser(field, analyzer);
	
			String line = queryString;
			
			line = line.trim();
	
			Query query = parser.parse(line);
			logger.info("Searching for: " + query.toString(field));
	
			res = doPagingSearchRecruitment(searcher, query, offset);
			
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
		String index = "ResumeIndex";

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
		Resume resume = resumeDao.getResume(employeeId);
		List<Education> eduList = resumeDao.listEducation(employeeId);
		List<WorkExperience> workList = resumeDao.listWorkExperience(employeeId);
		
		try {

			PreProcessor.loadSegmenter();
			PreProcessor.loadStopWords(null);
			PreProcessor.dealWithResume(resume, eduList, workList,FilePath.nlpPath + "tmp/resume.txt");
			
			KnowledgeBase.setPositionFile(100, FilePath.nlpPath+ "positionList top100.txt");
			KnowledgeBase.setSkillFile(100, FilePath.nlpPath+ "skillList top100.txt");
			KnowledgeBase.setProbFile(FilePath.nlpPath+ "contribution matrix.txt");
			KnowledgeBase.setSimilFile(FilePath.nlpPath+ "similarity matrix.txt");
			KnowledgeBase.loadKnowledgeBase();

			Classifier.setProbFile(15, 40, FilePath.nlpPath+ "classifier param.txt");
			Classifier.loadModel();
			Classifier classifier = new Classifier();

			double[] distribution;

			logger.info("职位文件分析中间结果*************************");
			PositionInfo positionInfo = new PositionInfo();
			positionInfo.process(FilePath.nlpPath + "tmp/resume.txt");
			for (int i = 0; i < positionInfo.skillVector.length - 1; i++) {
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
				List<RecruitmentBBS> recruitmentList = recruitmentDao.listRecruitmentBBS(i*100, 100);
				if(recruitmentList == null || recruitmentList.size() == 0)
					break;
				for (RecruitmentBBS recruitment : recruitmentList) {
					PreProcessor.dealWithRecruitment(recruitment, FilePath.nlpPath+ "tmp/recruitment.txt");
	
					logger.info("简历文件分析中间结果*************************");
					ResumeInfo resumeInfo = new ResumeInfo();
					resumeInfo.process(FilePath.nlpPath + "tmp/recruitment.txt");
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
					Relevance relevance = new Relevance(employeeId,
							recruitment.getId(), rel);
					relevanceDao.update(relevance);
					logger.info(rel);
	
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Transactional
	@Override
	public void updateRelevanceForEmployer(long recruitmentId) {
		RecruitmentBBS recruitment = recruitmentDao.loadRecruitmentBbs(recruitmentId);
		
		try {

			PreProcessor.loadSegmenter();
			PreProcessor.loadStopWords(null);
			PreProcessor.dealWithRecruitment(recruitment, FilePath.nlpPath+ "tmp/recruitment.txt");
			
			KnowledgeBase.setPositionFile(100, FilePath.nlpPath+ "positionList top100.txt");
			KnowledgeBase.setSkillFile(100, FilePath.nlpPath+ "skillList top100.txt");
			KnowledgeBase.setProbFile(FilePath.nlpPath+ "contribution matrix.txt");
			KnowledgeBase.setSimilFile(FilePath.nlpPath+ "similarity matrix.txt");
			KnowledgeBase.loadKnowledgeBase();

			Classifier.setProbFile(15, 40, FilePath.nlpPath+ "classifier param.txt");
			Classifier.loadModel();
			Classifier classifier = new Classifier();

			double[] distribution;

			logger.info("简历文件分析中间结果*************************");
			ResumeInfo resumeInfo = new ResumeInfo();
			resumeInfo.process(FilePath.nlpPath + "tmp/recruitment.txt");
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
			
			
			for(int i = 0;;i++) {
				List<Resume> resumeList = resumeDao.listResume(i*100, 100);
				if(resumeList == null || resumeList.size() == 0)
					break;
				for (Resume resume : resumeList) {
					List<Education> eduList = resumeDao.listEducation(resume.getEmployeeId());
					List<WorkExperience> workList = resumeDao.listWorkExperience(resume.getEmployeeId());
					PreProcessor.dealWithResume(resume, eduList, workList,FilePath.nlpPath + "tmp/resume.txt");

					logger.info("职位文件分析中间结果*************************");
					PositionInfo positionInfo = new PositionInfo();
					positionInfo.process(FilePath.nlpPath + "tmp/resume.txt");
					for (int j = 0; j < positionInfo.skillVector.length - 1; j++) {
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
					Relevance relevance = new Relevance(resume.getEmployeeId(),
							recruitmentId, rel);
					relevanceDao.update(relevance);
					logger.info(rel);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	@Override
	public Pager<MatchRecruitment> listMatchRecruitment(long employeeId,
			int offset) {
		List<MatchRecruitment> list = relevanceDao.listMatchRecruitment(employeeId, offset);
		Pager<MatchRecruitment> relevancePager = new Pager<>();
		relevancePager.setDatas(list);
		relevancePager.setOffset(offset);
		relevancePager.setSize(SystemContext.getSize());
		relevancePager.setTotal(relevanceDao.getRecruitmentNumber(employeeId));
		return relevancePager;
	}
	
	private Pager<AbstractRecruitment> doPagingSearchRecruitment(IndexSearcher searcher,
			Query query, int offset)
			throws IOException {

		Pager<AbstractRecruitment> res = new Pager<>();
		List<AbstractRecruitment> list = new ArrayList<>();
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
				Recruitment recruitment = recruitmentDao.loadRecruitment(id);
				if(recruitment.getDescription().length()>100) {
					recruitment.setDescription(recruitment.getDescription().substring(0, 100)+"...");
				}
				list.add(recruitment);
			} else {
				RecruitmentBBS recruitment = recruitmentDao.loadRecruitmentBbs(id);
				recruitment.setContent(recruitment.getContent().substring(0, 100)+"...");
				list.add(recruitment);
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
				Resume resume = resumeDao.getResume(Long.parseLong(doc.get("path")));
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
