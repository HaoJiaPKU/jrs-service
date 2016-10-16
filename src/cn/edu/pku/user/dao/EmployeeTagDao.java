package cn.edu.pku.user.dao;

import cn.edu.pku.user.domain.EmployeeTag;

public interface EmployeeTagDao {

	/**
	 * 添加标签
	 * @param employeeTag
	 */
	public void add(EmployeeTag employeeTag);
	
	/**
	 * 更新标签
	 * @param employeeTag
	 */
	public void update(EmployeeTag employeeTag);
}
