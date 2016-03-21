package cn.edu.pku.search.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import com.chenlb.mmseg4j.analysis.SimpleAnalyzer;

import cn.edu.pku.search.dao.RecruitmentDAO;
import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Recruitment;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {

	public static final Logger logger = Logger
			.getLogger(RecruitmentServiceImpl.class);

	RecruitmentDAO recruitmentDao;

	public RecruitmentDAO getRecruitmentDao() {
		return recruitmentDao;
	}

	@Resource
	public void setRecruitmentDao(RecruitmentDAO recruitmentDao) {
		this.recruitmentDao = recruitmentDao;
	}

	@Override
	public long addRecruitment(long employerId, String uploadTime,
			String modifyTime, String uploadIp, String modifyIp, String title, String degree,
			String city, String company, String position, String business,
			String scale, String type, String salary, int recruitNum,
			String description) {
		Recruitment recruitment = new Recruitment(employerId, uploadTime,
				modifyTime, uploadIp, modifyIp, title, degree, city, company,
				position, business, scale, type, salary, recruitNum,
				description);
		recruitmentDao.addRecruitment(recruitment);
		new Thread(new UpdateIndex(recruitment)).start();
		return recruitment.getId();
	}

	@Override
	public void addAttachment(long recruitmentId, String filepath) {
		Attachment attachment = new Attachment(recruitmentId, filepath);
		recruitmentDao.addAttachment(attachment);
	}

	@Override
	public List<Recruitment> listRecruitment(long employerId) {
		return recruitmentDao.listRecruitment(employerId);
	}

	@Override
	public Recruitment getRecruitment(long id) {
		return recruitmentDao.loadRecruitment(id);
	}

	@Override
	public List<Attachment> listAttachment(long recruitmentId) {
		return recruitmentDao.listAttachment(recruitmentId);
	}

	@Override
	public void deleteRecruitment(long id) {
		recruitmentDao.deleteRecruitment(id);
	}

	private class UpdateIndex implements Runnable {

		private Recruitment recruitment;

		public UpdateIndex(Recruitment recruitment) {
			this.recruitment = recruitment;
		}

		@Override
		public void run() {
			String indexPath = "index";
			// 以文件的形式创建索引
			Directory dir;
			try {
				dir = FSDirectory.open(Paths.get(indexPath));

				// 中文分词器
				Analyzer analyzer = new SimpleAnalyzer();
				IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

				// 追加索引文件
				iwc.setOpenMode(OpenMode.APPEND);

				IndexWriter writer = new IndexWriter(dir, iwc);

				Document doc = new Document();
				doc.add(new TextField("id", recruitment.getId() + "",
						Field.Store.YES));
				doc.add(new TextField("source", recruitment.getCompany(), Field.Store.YES));
				String content = recruitment.getBusiness() + " "
						+ recruitment.getCity() + " "
						+ recruitment.getCompany() + " "
						+ recruitment.getDegree() + " "
						+ recruitment.getDescription() + " "
						+ recruitment.getPosition() + " "
						+ recruitment.getType();
				doc.add(new TextField("content", content, Field.Store.NO));
				writer.addDocument(doc);

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
