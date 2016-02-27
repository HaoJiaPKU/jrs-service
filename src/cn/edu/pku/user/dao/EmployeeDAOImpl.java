package cn.edu.pku.user.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.user.domain.Employee;

@Repository
public class EmployeeDAOImpl extends HibernateDaoSupport implements EmployeeDAO{

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(Employee employee) {
		this.getHibernateTemplate().save(employee);
	}

	@Override
	public void update(Employee employee) {
		this.getHibernateTemplate().update(employee);
	}

	@Override
	public void delete(long id) {
		Employee employee = this.load(id);
		this.getHibernateTemplate().delete(employee);
	}

	@Override
	public Employee load(long id) {
		return this.getHibernateTemplate().load(Employee.class, id);
	}

	@Override
	public Employee load(String email) {
		Query query = this.getSession()
				.createQuery("from Employee where email=?");
		query.setParameter(0, email);
		return (Employee) query.uniqueResult();
	}

}
