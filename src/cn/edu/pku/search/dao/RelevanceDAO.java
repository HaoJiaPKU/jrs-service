package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.MatchRecruitment;
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Relevance;

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
	public List<MatchResume> listMatchResume(long recruitmentId, int offset);
	
	/**
	 * 列出匹配某求职者的一部分相关度信息
	 * @param employeeId 求职者ID
	 * @param offset 偏移量
	 * @return
	 */
	public List<Relevance> listRelevanceForEmployee(long employeeId, int offset);
	
	/**
	 * 得到招聘信息的总条数
	 * @param employeeId
	 * @return
	 */
	public long getRecruitmentNumber(long employeeId);
	
	/**
	 * 得到简历信息的总条数
	 * @param recruitmentId
	 * @return
	 */
	public long getResumeNumber(long recruitmentId);
}
