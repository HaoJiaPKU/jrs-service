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

import cn.edu.pku.search.domain.RecruitmentTag;
import cn.edu.pku.util.SystemContext;

/**
 * @author lanzheng
 *
 */

@Repository
public class RecuitmentTagDAOImpl extends HibernateDaoSupport implements RecruitmentTagDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void add(RecruitmentTag recruitmentTag) {
		this.getHibernateTemplate().save(recruitmentTag);
	}

	@Override
	public void update(RecruitmentTag recruitmentTag) {
		this.getHibernateTemplate().update(recruitmentTag);
	}

	@Override
	public List<RecruitmentTag> listRecruitmentTag(long recruitmentId) {
		Query query = this.getSession().createQuery(
				"from RecruitmentTag where recruitmentId=? order by tagName desc");
		query.setParameter(0, recruitmentId);
		query.setMaxResults(SystemContext.getSize());
		return query.list();
	}

}
