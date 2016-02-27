package cn.edu.pku.user.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.user.domain.Employer;

@Repository
public class EmployerDAOImpl extends HibernateDaoSupport implements EmployerDAO,Serializable{

	private static final long serialVersionUID = 1L;

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public long add(Employer employer) {
		return (Long)this.getHibernateTemplate().save(employer);
	}

	@Override
	public void update(Employer employer) {
		this.getHibernateTemplate().update(employer);
	}

	@Override
	public void delete(long id) {
		Employer employer = this.load(id);
		this.getHibernateTemplate().delete(employer);
	}

	@Override
	public Employer load(long id) {
		return this.getHibernateTemplate().load(Employer.class, id);
	}

	@Override
	public Employer loadByUsername(String username) {
		Query query = this.getSession()
				.createQuery("from Employer where email=?");
		query.setParameter(0, username);
		return (Employer) query.uniqueResult();
	}


}
