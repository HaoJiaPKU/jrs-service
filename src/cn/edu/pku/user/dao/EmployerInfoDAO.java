package cn.edu.pku.user.dao;

import cn.edu.pku.user.domain.EmployerInfo;

public interface EmployerInfoDAO {

	public void add(EmployerInfo employerinfo);
	public void update(EmployerInfo employerinfo);
	public void delete(long id);
	public EmployerInfo load(long id);
}
