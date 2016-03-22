package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Sun Xiaowei
 *
 */
@Entity
@Table(name="relevance")
public class Relevance implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private long resumeId;
	private int recruitmentSource;
	private long recruitmentId;
	private double relevance;
	
	public Relevance(){}


	public Relevance(long employeeId, long resumeId, int recruitmentSource,
			long recruitmentId, double relevance) {
		super();
		this.employeeId = employeeId;
		this.resumeId = resumeId;
		this.recruitmentSource = recruitmentSource;
		this.recruitmentId = recruitmentId;
		this.relevance = relevance;
	}


	@Id
	@Column(name="employee_id")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	@Id
	@Column(name="recruitment_id")
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
	@Id
	@Column(name="resume_id")
	public long getResumeId() {
		return resumeId;
	}

	public void setResumeId(long resumeId) {
		this.resumeId = resumeId;
	}
	@Id
	@Column(name="recruitment_source")
	public int getRecruitmentSource() {
		return recruitmentSource;
	}

	public void setRecruitmentSource(int recruitmentSource) {
		this.recruitmentSource = recruitmentSource;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (employeeId ^ (employeeId >>> 32));
		result = prime * result
				+ (int) (recruitmentId ^ (recruitmentId >>> 32));
		result = prime * result + recruitmentSource;
		result = prime * result + (int) (resumeId ^ (resumeId >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relevance other = (Relevance) obj;
		if (employeeId != other.employeeId)
			return false;
		if (recruitmentId != other.recruitmentId)
			return false;
		if (recruitmentSource != other.recruitmentSource)
			return false;
		if (resumeId != other.resumeId)
			return false;
		return true;
	}


}
