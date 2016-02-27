package cn.edu.pku.user.dao;

import cn.edu.pku.user.domain.Employer;

public interface EmployerDAO {

	public long add(Employer employer);
	public void update(Employer employer);
	public void delete(long id);
	public Employer load(long id);
	public Employer loadByUsername(String username);
}
