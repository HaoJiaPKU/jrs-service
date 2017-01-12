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

import cn.edu.pku.search.dao.PositionDAO;
import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.PositionJobpopo;

@Service
public class PositionServiceImpl implements PositionService {

	public static final Logger logger = Logger
			.getLogger(PositionServiceImpl.class);

	PositionDAO positionDao;

	public PositionDAO getPositionDao() {
		return positionDao;
	}

	@Resource
	public void setPositionDao(PositionDAO positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public long addPosition(long employerId, String uploadTime,
			String modifyTime, String uploadIp, String modifyIp, String title, String degree,
			String city, String company, String position, String business,
			String scale, String type, String salary, int recruitNum,
			String description) {
		PositionJobpopo positionJobpopo = new PositionJobpopo(employerId, uploadTime,
				modifyTime, uploadIp, modifyIp, title, degree, city, company,
				position, business, scale, type, salary, recruitNum,
				description);
		positionDao.addPosition(positionJobpopo);
		new Thread(new UpdateIndex(positionJobpopo)).start();
		return positionJobpopo.getId();
	}

	@Override
	public void addAttachment(long positionId, String filepath) {
		Attachment attachment = new Attachment(positionId, filepath);
		positionDao.addAttachment(attachment);
	}

	@Override
	public List<PositionJobpopo> listPosition(long employerId) {
		return positionDao.listPosition(employerId);
	}

	@Override
	public PositionJobpopo getPosition(long id) {
		return positionDao.loadPosition(id);
	}

	@Override
	public List<Attachment> listAttachment(long positionId) {
		return positionDao.listAttachment(positionId);
	}

	@Override
	public void deletePosition(long id) {
		positionDao.deletePosition(id);
	}

	private class UpdateIndex implements Runnable {

		private PositionJobpopo position;

		public UpdateIndex(PositionJobpopo position) {
			this.position = position;
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
				doc.add(new TextField("id", position.getId() + "",
						Field.Store.YES));
				doc.add(new TextField("source", position.getCompany(), Field.Store.YES));
				String content = position.getBusiness() + " "
						+ position.getCity() + " "
						+ position.getCompany() + " "
						+ position.getDegree() + " "
						+ position.getDescription() + " "
						+ position.getPosition() + " "
						+ position.getType();
				doc.add(new TextField("content", content, Field.Store.NO));
				writer.addDocument(doc);

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
