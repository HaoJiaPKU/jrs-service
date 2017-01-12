package cn.edu.pku.search.service;

import cn.edu.pku.search.domain.AbstractPosition;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.MatchPosition;
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Pager;

public interface SearchService {

	/**
	 * 搜索招聘信息
	 * @param field lucene的搜索域
	 * @param queryString 查询关键字
	 * @param offset 偏移量
	 * @return 
	 */
	public Pager<AbstractPosition> searchPosition(String field, String queryString,int offset);

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
	 * @param positionId 招聘信息ID
	 */
	public void updateRelevanceForEmployer(long positionId);
	
	/**
	 * 为求职者列出匹配的招聘信息
	 * @param employeeId 求职者ID
	 * @param offset 偏移量
	 * @return
	 */
	public Pager<MatchPosition> listMatchPosition(long employeeId, int offset);
	
	/**
	 * 为企业列出匹配的简历信息
	 * @param employerId
	 * @param offset
	 * @return
	 */
	public Pager<MatchResume> listMatchResume(long positionId, int offset);
	
}
