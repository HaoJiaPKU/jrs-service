package cn.edu.pku.user.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.user.domain.EmployerInfo;

@Repository
public class EmployerInfoDAOImpl extends HibernateDaoSupport implements EmployerInfoDAO{

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(EmployerInfo employerinfo) {
		this.getHibernateTemplate().save(employerinfo);
	}

	@Override
	public void update(EmployerInfo employerinfo) {
		this.getHibernateTemplate().update(employerinfo);
	}

	@Override
	public void delete(long id) {
		EmployerInfo employerInfo = this.load(id);
		this.getHibernateTemplate().delete(employerInfo);
	}

	@Override
	public EmployerInfo load(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
