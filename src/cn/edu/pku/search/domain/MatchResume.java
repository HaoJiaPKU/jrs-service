package cn.edu.pku.search.domain;

import java.io.Serializable;

/**
 * 匹配的简历信息
 * @author Sun Xiaowei
 *
 */
public class MatchResume implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long recruitmentId;
	private double relevance;
	private Resume resume;
	
	public MatchResume(long recruitmentId, double relevance, Resume resume) {
		super();
		this.recruitmentId = recruitmentId;
		this.relevance = relevance;
		this.resume = resume;
	}
	public long getRecruitmentId() {
		return recruitmentId;
	}
	public void setRecruitmentId(long recruitmentId) {
		this.recruitmentId = recruitmentId;
	}
	public double getRelevance() {
		return relevance;
	}
	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	public Resume getResume() {
		return resume;
	}
	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
