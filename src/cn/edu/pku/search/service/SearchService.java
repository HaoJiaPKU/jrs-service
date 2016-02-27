package cn.edu.pku.search.service;

import cn.edu.pku.search.domain.AbstractRecruitment;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.MatchRecruitment;
import cn.edu.pku.search.domain.Pager;

public interface SearchService {

	/**
	 * 搜索招聘信息
	 * @param field lucene的搜索域
	 * @param queryString 查询关键字
	 * @param offset 偏移量
	 * @return 
	 */
	public Pager<AbstractRecruitment> searchRecruitment(String field, String queryString,int offset);

	/**
	 * 搜索简历信息
	 * @param field lucene的搜索域
	 * @param queryString 查询关键字
	 * @param offset 偏移量
	 * @return
	 */
	public Pager<AbstractResume> searchResume(String field, String queryString,int offset);
	
	/**
	 * 为求职者更新简历与招聘信息相关度
	 * @param employeeId 求职者ID
	 */
	public void updateRelevanceForEmployee(long employeeId);
	
	/**
	 * 为企业更新简历与招聘信息相关度
	 * @param recruitmentId 招聘信息ID
	 */
	public void updateRelevanceForEmployer(long recruitmentId);
	
	/**
	 * 为求职者列出匹配的招聘信息
	 * @param employeeId 求职者ID
	 * @param offset 偏移量
	 * @return
	 */
	public Pager<MatchRecruitment> listMatchRecruitment(long employeeId, int offset);
	
}
