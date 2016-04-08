package cn.edu.pku.user.service;

import cn.edu.pku.user.domain.Employee;

public interface EmployeeService {

	public Employee login(String email,String password);
	public Employee regist(String email,String password);
	
	public void sendVerificationEmail(Employee employee);
	public void sendVerificationAgain(long id);
	public Employee activate(long id, String password);
	public Employee uploadResume(long id);
	public Employee updateSubscription(long id, int subscriptionNum, int recFreq);
}
