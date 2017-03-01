package cn.edu.pku.search.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.Industry;

@Repository
public class IndustryDAOImpl extends HibernateDaoSupport implements
		IndustryDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Industry> loadAllIndustry() {
		// TODO Auto-generated method stub
		Query query = this.getSession()
				.createQuery("from Industry order by num desc");
		return query.list();
	}
	
}
