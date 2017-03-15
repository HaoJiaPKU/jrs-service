package cn.edu.pku.search.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Resume51Job;
import cn.edu.pku.search.domain.ResumeJobpopo;
import cn.edu.pku.search.domain.WorkExperience;

@Repository
public class ResumeDAOImpl extends HibernateDaoSupport implements ResumeDAO {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void addEducation(Education education) {
		this.getHibernateTemplate().save(education);

	}

	@Override
	public void addWorkExperience(WorkExperience workExperience) {
		this.getHibernateTemplate().save(workExperience);
	}

	@Override
	public void deleteEducation(long id) {
		Education education = this.getEducation(id);
		this.deleteEducation(education);
	}

	@Override
	public void deleteEducation(Education education) {
		this.getHibernateTemplate().delete(education);
	}
	
	@Override
	public void deleteEducationByEmployeeId(long employeeId) {
		Query query = this.getSession().createQuery("delete from Education where employeeId = ?");
		query.setParameter(0, employeeId);
		query.executeUpdate();
	}
	
	@Override
	public void deleteWorkExperience(long id) {
		WorkExperience workExperience = this.getWorkExperience(id);
		this.deleteWorkExperience(workExperience);
	}

	@Override
	public void deleteWorkExperience(WorkExperience workExperience) {
		this.getHibernateTemplate().delete(workExperience);
	}
	
	@Override
	public void deleteWorkExperienceByEmployeeId(long employeeId) {
		Query query = this.getSession().createQuery("delete from WorkExperience where employeeId = ?");
		query.setParameter(0, employeeId);
		query.executeUpdate();
	}

	@Override
	public void updateResume(ResumeJobpopo resume) {
		this.getHibernateTemplate().update(resume);
	}
	
	@Override
	public void updateEducation(Education education) {
		this.getHibernateTemplate().update(education);
	}

	@Override
	public void updateWorkExperience(WorkExperience workExperience) {
		this.getHibernateTemplate().update(workExperience);
	}

	@Override
	public List<Education> listEducation(long employeeId) {
		Query query = this.getSession().createQuery("from Education where employeeId=?");
		query.setParameter(0, employeeId);
		return query.list();
	}

	@Override
	public List<WorkExperience> listWorkExperience(long employeeId) {
		Query query = this.getSession()
				.createQuery("from WorkExperience where employeeId=?");
		query.setParameter(0, employeeId);
		return query.list();
	}
	
	@Override
	public List<ResumeJobpopo> listResume(int offset,int size) {
		Query query = this.getSession().createQuery("from ResumeJobpopo");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}
	
	@Override
	public List<Resume51Job> listResume51Job(int offset, int size) {
		Query query = this.getSession().createQuery("from Resume51Job");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public void addResume(ResumeJobpopo resume) {
		this.getHibernateTemplate().saveOrUpdate(resume);
	}
	
	@Override
	public void deleteResume(long employeeId) {
		ResumeJobpopo resume = this.getResume(employeeId);
		this.getHibernateTemplate().delete(resume);
	}

	@Override
	public ResumeJobpopo getResume(long employeeId) {
		return this.getHibernateTemplate().load(ResumeJobpopo.class, employeeId);
	}

	@Override
	public WorkExperience getWorkExperience(long id) {
		return this.getHibernateTemplate().load(WorkExperience.class, id);
	}

	@Override
	public Education getEducation(long id) {
		return this.getHibernateTemplate().load(Education.class, id);
	}

	@Override
	public Resume51Job getResume51Job(long id) {
		return this.getHibernateTemplate().load(Resume51Job.class, id);
	}

}
