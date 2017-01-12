package cn.edu.pku.search.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.PositionJobpopo;
import cn.edu.pku.search.domain.Position;

@Repository
public class PositionDAOImpl extends HibernateDaoSupport implements
		PositionDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public void addPosition(PositionJobpopo position) {
		this.getHibernateTemplate().save(position);
	}

	@Override
	public void addAttachment(Attachment attachment) {
		this.getHibernateTemplate().save(attachment);
	}

	@Override
	public List<PositionJobpopo> listPosition(long employerId) {
		Query query = this.getSession().createQuery(
				"from PositionJobpopo where employerId=?");
		query.setParameter(0, employerId);
		return query.list();
	}

	@Override
	public PositionJobpopo loadPosition(long id) {
		return this.getHibernateTemplate().load(PositionJobpopo.class, id);
	}
	
	@Override
	public Position loadPositionBbs(long id) {
		return this.getHibernateTemplate().load(Position.class, id);
	}

	@Override
	public List<Attachment> listAttachment(long positionId) {
		Query query = this.getSession()
				.createQuery("from Attachment where positionId=?");
		query.setParameter(0, positionId);
		return query.list();
	}

	@Override
	public void deletePosition(long id) {
		PositionJobpopo position = this.loadPosition(id);
		List<Attachment> list = this.listAttachment(id);
		for (Attachment att : list) {
			this.deleteAttachment(att);
		}
		this.getHibernateTemplate().delete(position);
	}

	@Override
	public void deleteAttachment(Attachment attachment) {
		this.getHibernateTemplate().delete(attachment);

	}

	@Override
	public List<PositionJobpopo> listAllPosition() {
		return this.getSession().createQuery("from PositionJobpopo").list();
	}

	@Override
	public List<Position> listPositionBBS(int offset, int size) {
		Query query = this.getSession().createQuery("from Position");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public List<PositionJobpopo> listPosition(int offset, int size) {
		Query query = this.getSession().createQuery("from PositionJobpopo");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}

	
	@Override
	public void update(PositionJobpopo position) {
		this.getHibernateTemplate().update(position);
	}
	

	@Override
	public void updateBBS(Position positionBBS) {
		this.getHibernateTemplate().update(positionBBS);
	}

}
