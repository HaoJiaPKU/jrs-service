package cn.edu.pku.user.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.util.SystemContext;

@Repository
public class EmployeeTagDAOIpml extends HibernateDaoSupport implements EmployeeTagDAO {

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
		return query.list();
	}

	
	@Override
	public void deleteEmployeeTag(Long employeeId) {
		// TODO Auto-generated method stub
		List<EmployeeTag> list = this.listEmployeeTag(employeeId);
		for (EmployeeTag tag : list) {
			this.delete(tag);
		}
	}

	@Override
	public void delete(EmployeeTag employeeTag) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(employeeTag);
	}

}
