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
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Pager;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.RecruitmentBBS;
import cn.edu.pku.search.domain.Relevance;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.Resume51Job;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.user.dao.EmployeeDAO;
import cn.edu.pku.user.dao.EmployeeTagDao;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.util.FilePath;
import cn.edu.pku.util.SystemContext;

@Service
public class SearchServiceImpl implements SearchService {

	public static final Logger logger = Logger.getLogger(SearchServiceImpl.class);
	
	ResumeDAO resumeDao;
	RecruitmentDAO recruitmentDao;
	RelevanceDAO relevanceDao;
	EmployeeDAO employeeDAO;
	EmployeeTagDao employeeTagDAO;

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

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	@Resource
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public EmployeeTagDao getEmployeeTagDAO() {
		return employeeTagDAO;
	}

	@Resource
	public void setEmployeeTagDAO(EmployeeTagDao employeeTagDAO) {
		this.employeeTagDAO = employeeTagDAO;
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
		String index = FilePath.recruitmentIndex;

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
			
			// TODO hasTag is supposed to be boolean
			Employee employee = employeeDAO.load(employeeId);
			if (employee.getHasTag() == 0) {
				for (int i = 0; i < distribution.length; i ++) {
					EmployeeTag employeeTag = new EmployeeTag(
							employeeId, KnowledgeBase.positionList[i], distribution[i]);
					employeeTagDAO.add(employeeTag);
				}
				employee.setHasTag(1);
				employeeDAO.update(employee);
			}
			
			for(int i = 0;;i++) {
				List<RecruitmentBBS> recruitmentList = recruitmentDao.listRecruitmentBBS(i*100, 100);
				if(recruitmentList == null || recruitmentList.size() == 0)
					break;
				for (RecruitmentBBS recruitment : recruitmentList) {
					PreProcessor.dealWithString(recruitment.toString(), FilePath.nlpPath+ "tmp/recruitment.txt");
	
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
					Relevance relevance = new Relevance(employeeId, 0, 2, recruitment.getId(), rel);
					relevanceDao.update(relevance);
					logger.info(rel);
	
				}
			}
			
			for(int i = 0;;i++) {
				List<Recruitment> recruitmentList = recruitmentDao.listRecruitment(i*100, 100);
				if(recruitmentList == null || recruitmentList.size() == 0)
					break;
				for (Recruitment recruitment : recruitmentList) {
					PreProcessor.dealWithString(recruitment.toString(), FilePath.nlpPath+ "tmp/recruitment.txt");
	
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
					Relevance relevance = new Relevance(employeeId, 0, 1, recruitment.getId(), rel);
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
		Recruitment recruitment = recruitmentDao.loadRecruitment(recruitmentId);
		
		try {

			PreProcessor.loadSegmenter();
			PreProcessor.loadStopWords(null);
			PreProcessor.dealWithString(recruitment.toString(), FilePath.nlpPath+ "tmp/recruitment.txt");
			
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
					Relevance relevance = new Relevance(resume.getEmployeeId(),0,1,
							recruitmentId, rel);
					relevanceDao.update(relevance);
					logger.info(rel);
				}
			}
			
			for(int i = 0;;i++) {
				List<Resume51Job> resumeList = resumeDao.listResume51Job(i*100, 100);
				if(resumeList == null || resumeList.size() == 0)
					break;
				for (Resume51Job resume : resumeList) {
					PreProcessor.dealWithText("../webapps/"+resume.getPath(),FilePath.nlpPath + "tmp/resume.txt");

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
					Relevance relevance = new Relevance(0,resume.getId(),1,
							recruitmentId, rel);
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
	public Pager<MatchRecruitment> listMatchRecruitment(long employeeId,
			int offset) {
		List<Relevance> list = relevanceDao.listRelevanceForEmployee(employeeId, offset);
		List<MatchRecruitment> matchList = new ArrayList<>();
		for(Relevance rel : list) {
			if(rel.getRecruitmentSource() == 1) {
				Recruitment rec = recruitmentDao.loadRecruitment(rel.getRecruitmentId());
				rec.setDescription(rec.getDescription().substring(0, 100)+"...");//为了前台只显示两三行内容
				MatchRecruitment match = new MatchRecruitment(employeeId, rel.getRelevance(), rec);
				matchList.add(match);
			} else if(rel.getRecruitmentSource() == 2) {
				RecruitmentBBS rec = recruitmentDao.loadRecruitmentBbs(rel.getRecruitmentId());
				rec.setContent(rec.getContent().substring(0, 100)+"...");//为了前台只显示两三行内容
				MatchRecruitment match = new MatchRecruitment(employeeId, rel.getRelevance(), rec);
				matchList.add(match);
			}
		}
		Pager<MatchRecruitment> relevancePager = new Pager<>();
		relevancePager.setDatas(matchList);
		relevancePager.setOffset(offset);
		relevancePager.setSize(SystemContext.getSize());
		relevancePager.setTotal(relevanceDao.getRecruitmentNumber(employeeId));
		return relevancePager;
	}
	
	@Transactional
	@Override
	public Pager<MatchResume> listMatchResume(long recruitmentId, int offset) {
		List<Relevance> list = relevanceDao.listRelevanceForEmployer(recruitmentId, offset);
		List<MatchResume> matchList = new ArrayList<>();
		for(Relevance rel : list) {
			if(rel.getEmployeeId() == 0) {
				Resume51Job resume = resumeDao.getResume51Job(rel.getResumeId());
				MatchResume match = new MatchResume(recruitmentId, rel.getRelevance(), resume);
				matchList.add(match);
			} else {
				Resume resume = resumeDao.getResume(rel.getEmployeeId());
				MatchResume match = new MatchResume(recruitmentId, rel.getRelevance(), resume);
				matchList.add(match);
			}
		}
		Pager<MatchResume> relevancePager = new Pager<>();
		relevancePager.setDatas(matchList);
		relevancePager.setOffset(offset);
		relevancePager.setSize(SystemContext.getSize());
		relevancePager.setTotal(relevanceDao.getResumeNumber(recruitmentId));
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
				recruitment.setContent(recruitment.getContent().substring(0, 100)+"...");//为了前台只显示两三行内容
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
