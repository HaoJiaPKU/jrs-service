package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.RecruitmentTag;

public interface RecruitmentTagDAO {

	/**
	 * 添加标签
	 * @param recruitmentTag
	 */
	public void add(RecruitmentTag recruitmentTag);
	
	/**
	 * 更新标签
	 * @param recruitmentTag
	 */
	public void update(RecruitmentTag recruitmentTag);

	/**
	 * 列出职位标签
	 * @param recruitmentTag
	 */
	public List<RecruitmentTag> listRecruitmentTag(long recruitmentId);
}
