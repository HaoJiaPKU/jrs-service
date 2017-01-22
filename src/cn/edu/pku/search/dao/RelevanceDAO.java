package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.Relevance;
import cn.edu.pku.user.domain.EmployeeTag;

public interface RelevanceDAO {

	/**
	 * 更新相关度
	 * @param relevance
	 */
	public void update(Relevance relevance);
	
	/**
	 * 列出匹配某求职者的招聘信息
	 * @param employeeId 求职者ID
	 * @param offset 偏移量
	 * @return
	 */
	public List<Relevance> listRelevanceForEmployer(long positionId, int offset);
	
	/**
	 * 列出匹配某求职者的一部分相关度信息
	 * @param employeeId 求职者ID
	 * @param offset 偏移量
	 * @return
	 */
	public List<Relevance> listRelevanceForEmployee(long employeeId, int offset);
	
	/**
	 * 列出匹配某求职者的全部相关度信息
	 * @param employeeId 求职者ID
	 * @return
	 */
	public List<Relevance> listRelevanceForEmployee(long employeeId);
	
	/**
	 * 得到招聘信息的总条数
	 * @param employeeId
	 * @return
	 */
	public long getPositionNumber(long employeeId);
	
	/**
	 * 得到简历信息的总条数
	 * @param positionId
	 * @return
	 */
	public long getResumeNumber(long positionId);
	
	/**
	 * 删除employee的相关度
	 * @param employeeId
	 */
	public void deleteRelevanceByEmployeeId(Long employeeId);
	
	/**
	 * 删除相关度
	 * @param employeeTag
	 */
	public void delete(Relevance relevance);
}
