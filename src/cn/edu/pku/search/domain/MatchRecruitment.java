package cn.edu.pku.search.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 匹配的招聘信息
 * @author Sun Xiaowei
 *
 */
public class MatchRecruitment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private double relevance;
	private AbstractRecruitment recruitment;
	private List<RecruitmentTag> recruitmentTagList;
	
	public MatchRecruitment(long employeeId, double relevance,
			AbstractRecruitment recruitment, List<RecruitmentTag> recruitmentTagList) {
		super();
		this.employeeId = employeeId;
		this.relevance = relevance;
		this.recruitment = recruitment;
		this.recruitmentTagList = recruitmentTagList;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public double getRelevance() {
		return relevance;
	}
	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	public AbstractRecruitment getRecruitment() {
		return recruitment;
	}
	public void setRecruitment(AbstractRecruitment recruitment) {
		this.recruitment = recruitment;
	}
	public List<RecruitmentTag> getRecruitmentTagList() {
		return recruitmentTagList;
	}
	public void setRecruitmentTagList(List<RecruitmentTag> recruitmentTagList) {
		this.recruitmentTagList = recruitmentTagList;
	}
	
	
}
