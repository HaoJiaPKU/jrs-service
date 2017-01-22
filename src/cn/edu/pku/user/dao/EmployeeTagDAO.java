package cn.edu.pku.user.dao;

import java.util.List;

import cn.edu.pku.user.domain.EmployeeTag;

public interface EmployeeTagDAO {

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

	/**
	 * 删除标签
	 * @param employeeId
	 */
	public void deleteEmployeeTag(Long employeeId);
	
	/**
	 * 删除标签
	 * @param employeeTag
	 */
	public void delete(EmployeeTag employeeTag);
	
	/**
	 * 列出求职者标签
	 * @param employeeTag
	 */
	public List<EmployeeTag> listEmployeeTag(long employeeId);
}
