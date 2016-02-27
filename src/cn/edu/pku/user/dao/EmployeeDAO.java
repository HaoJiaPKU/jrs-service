package cn.edu.pku.user.dao;

import cn.edu.pku.user.domain.Employee;

public interface EmployeeDAO {

	/**
	 * 添加用户
	 * @param employee
	 */
	public void add(Employee employee);
	
	/**
	 * 更新用户
	 * @param employee
	 */
	public void update(Employee employee);
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * 得到某一用户
	 * @param id
	 * @return
	 */
	public Employee load(long id);
	
	/**
	 * 得到某一用户
	 * @param email
	 * @return
	 */
	public Employee load(String email);
}
