package cn.edu.pku.user.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.user.domain.EmployeeTag;

@Repository
public class EmployeeTagDaoImpl extends HibernateDaoSupport implements EmployeeTagDao {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(EmployeeTag employeeTag) {
		this.getHibernateTemplate().save(employeeTag);
	}

	@Override
	public void update(EmployeeTag employeeTag) {
		this.getHibernateTemplate().update(employeeTag);
	}

}
