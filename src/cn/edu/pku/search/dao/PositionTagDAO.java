package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.PositionTag;
import cn.edu.pku.user.domain.EmployeeTag;

public interface PositionTagDAO {

	/**
	 * 添加标签
	 * @param positionTag
	 */
	public void add(PositionTag positionTag);
	
	/**
	 * 更新标签
	 * @param positionTag
	 */
	public void update(PositionTag positionTag);

	/**
	 * 删除标签
	 * @param positionId
	 */
	public void deletePositionTag(Long positionId);
	
	/**
	 * 删除标签
	 * @param positionTag
	 */
	public void delete(PositionTag positionTag);
	
	/**
	 * 列出职位标签
	 * @param positionTag
	 */
	public List<PositionTag> listPositionTag(long positionId);
}
