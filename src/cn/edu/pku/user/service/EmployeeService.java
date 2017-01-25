package cn.edu.pku.user.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.domain.EmployeeTag;

public interface EmployeeService {

	public Employee login(String email,String password);
	public Employee regist(String email,String password);
	
	public void sendVerificationEmail(Employee employee);
	public void sendVerificationAgain(long id);
	public Employee activate(long id, String password);
	public Employee uploadResume(long id);
	public Employee updateSubscription(long id, int subscriptionNum, int recFreq);
	public List<EmployeeTag> listEmployeeTag(long employeeId);
	public HashMap<String, HashSet<String>> loadAllIndustry();
}
