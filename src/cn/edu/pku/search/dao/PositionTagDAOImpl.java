/**
 * 
 */
package cn.edu.pku.search.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.PositionTag;
import cn.edu.pku.util.SystemContext;

/**
 * @author lanzheng
 *
 */

@Repository
public class PositionTagDAOImpl extends HibernateDaoSupport implements PositionTagDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(PositionTag positionTag) {
		this.getHibernateTemplate().save(positionTag);
	}

	@Override
	public void update(PositionTag positionTag) {
		this.getHibernateTemplate().update(positionTag);
	}

	@Override
	public List<PositionTag> listPositionTag(long positionId) {
		Query query = this.getSession().createQuery(
				"from PositionTag where positionId=? order by tagName desc");
		query.setParameter(0, positionId);
		return query.list();
	}

}
