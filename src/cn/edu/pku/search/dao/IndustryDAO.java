package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.Industry;

public interface IndustryDAO {

	/**
	 * 得到行业信息
	 */
	public List<Industry> loadAllIndustry();

}
