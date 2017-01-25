package cn.edu.pku.search.domain;

import java.io.Serializable;

/**
 * 匹配的简历信息
 * @author lanzheng
 *
 */
public class MatchResume implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long positionId;
	private double relevance;
	private AbstractResume resume;
	
	public MatchResume(long positionId, double relevance, AbstractResume resume) {
		super();
		this.positionId = positionId;
		this.relevance = relevance;
		this.resume = resume;
	}
	public long getPositionId() {
		return positionId;
	}
	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}
	public double getRelevance() {
		return relevance;
	}
	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	public AbstractResume getResume() {
		return resume;
	}
	public void setResume(AbstractResume resume) {
		this.resume = resume;
	}
	

}
