package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.MatchRecruitment;
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
	public List<MatchRecruitment> listMatchRecruitment(long employeeId, int offset);
	
	/**
	 * 得到招聘信息的总条数
	 * @param employeeId
	 * @return
	 */
	public long getRecruitmentNumber(long employeeId);
}
