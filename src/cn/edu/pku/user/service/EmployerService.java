package cn.edu.pku.user.service;

import cn.edu.pku.user.domain.Employer;

public interface EmployerService {

	public Employer login(String email,String password);
	public Employer regist(String companyName,String province,String city,String linkman,String gender,String phone,String email,String password);

	public void sendVerificationEmail(Employer employer);
	public Employer activate(long id, String password);
}
