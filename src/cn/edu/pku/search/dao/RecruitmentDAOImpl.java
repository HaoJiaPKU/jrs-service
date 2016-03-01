package cn.edu.pku.search.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.RecruitmentBBS;

@Repository
public class RecruitmentDAOImpl extends HibernateDaoSupport implements
		RecruitmentDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public void addRecruitment(Recruitment recruitment) {
		this.getHibernateTemplate().save(recruitment);
	}

	@Override
	public void addAttachment(Attachment attachment) {
		this.getHibernateTemplate().save(attachment);
	}

	@Override
	public List<Recruitment> listRecruitment(long employerId) {
		Query query = this.getSession().createQuery(
				"from Recruitment where employerId=?");
		query.setParameter(0, employerId);
		return query.list();
	}

	@Override
	public Recruitment loadRecruitment(long id) {
		return this.getHibernateTemplate().load(Recruitment.class, id);
	}
	
	@Override
	public RecruitmentBBS loadRecruitmentBbs(long id) {
		return this.getHibernateTemplate().load(RecruitmentBBS.class, id);
	}

	@Override
	public List<Attachment> listAttachment(long recruitmentId) {
		Query query = this.getSession()
				.createQuery("from Attachment where recruitmentId=?");
		query.setParameter(0, recruitmentId);
		return query.list();
	}

	@Override
	public void deleteRecruitment(long id) {
		Recruitment recruitment = this.loadRecruitment(id);
		List<Attachment> list = this.listAttachment(id);
		for (Attachment att : list) {
			this.deleteAttachment(att);
		}
		this.getHibernateTemplate().delete(recruitment);
	}

	@Override
	public void deleteAttachment(Attachment attachment) {
		this.getHibernateTemplate().delete(attachment);

	}

	@Override
	public List<Recruitment> listAllRecruitment() {
		return this.getSession().createQuery("from Recruitment").list();
	}

	@Override
	public List<RecruitmentBBS> listRecruitmentBBS(int offset, int size) {
		Query query = this.getSession().createQuery("from RecruitmentBBS");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public List<Recruitment> listRecruitment(int offset, int size) {
		Query query = this.getSession().createQuery("from Recruitment");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}

}
