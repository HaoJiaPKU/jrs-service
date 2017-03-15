package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Resume51Job;
import cn.edu.pku.search.domain.ResumeJobpopo;
import cn.edu.pku.search.domain.WorkExperience;

public interface ResumeDAO {

	/**
	 * 添加教育经历
	 * @param education
	 */
	public void addEducation(Education education);
	
	/**
	 * 添加工作经历
	 * @param workExperience
	 */
	public void addWorkExperience(WorkExperience workExperience);
	
	/**
	 * 删除教育经历
	 * @param id
	 */
	public void deleteEducation(long id);
	
	/**
	 * 删除教育经历
	 * @param education
	 */
	public void deleteEducation(Education education);
	
	/**
	 * 根据求职者ID删除教育经历
	 * @param employeeId
	 */
	public void deleteEducationByEmployeeId(long employeeId);
	
	/**
	 * 删除工作经历
	 * @param id
	 */
	public void deleteWorkExperience(long id);
	
	/**
	 * 删除工作经历
	 * @param workExperience
	 */
	public void deleteWorkExperience(WorkExperience workExperience);
	
	/**
	 * 根据求职者ID删除工作经历
	 * @param employeeId
	 */
	public void deleteWorkExperienceByEmployeeId(long employeeId);
	
	/**
	 * 更新简历
	 * @param resume
	 */
	public void updateResume(ResumeJobpopo resume);
	
	/**
	 * 更新教育经历
	 * @param education
	 */
	public void updateEducation(Education education);
	
	/**
	 * 更新工作经历
	 * @param workExperience
	 */
	public void updateWorkExperience(WorkExperience workExperience);
	
	/**
	 * 得到某用户的教育经历信息
	 * @param employeeId
	 * @return
	 */
	public List<Education> listEducation(long employeeId);
	
	/**
	 * 得到某用户的工作经历信息
	 * @param employeeId
	 * @return
	 */
	public List<WorkExperience> listWorkExperience(long employeeId);
	
	/**
	 * 列出一部分简历
	 * @param offset 偏移量
	 * @param size 信息条数
	 * @return
	 */
	public List<ResumeJobpopo> listResume(int offset,int size);
	
	/**
	 * 列出一部分51job的简历
	 * @param offset 偏移量
	 * @param size 信息条数
	 * @return
	 */
	public List<Resume51Job> listResume51Job(int offset, int size);
	
	/**
	 * 添加简历
	 * @param resume
	 */
	public void addResume(ResumeJobpopo resume);
	
	/**
	 * 删除简历
	 * @param employeeId
	 */
	public void deleteResume(long employeeId);
	
	/**
	 * 得到某用户的简历
	 * @param employeeId
	 * @return
	 */
	public ResumeJobpopo getResume(long employeeId);
	
	/**
	 * 得到51job中的简历
	 * @param id 简历ID
	 * @return
	 */
	public Resume51Job getResume51Job(long id);
	
	/**
	 * 得到工作经历
	 * @param id 工作经历ID
	 * @return
	 */
	public WorkExperience getWorkExperience(long id);
	
	/**
	 * 得到教育经历
	 * @param id 教育经历ID
	 * @return
	 */
	public Education getEducation(long id);
}
