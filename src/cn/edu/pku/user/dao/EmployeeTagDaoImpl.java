package cn.edu.pku.user.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.util.SystemContext;

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

	@Override
	public List<EmployeeTag> listEmployeeTag(long employeeId) {
		Query query = this.getSession().createQuery(
				"from EmployeeTag where employeeId=? order by tagName desc");
		query.setParameter(0, employeeId);
		query.setMaxResults(SystemContext.getSize());
		return query.list();
	}

}
