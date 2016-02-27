package cn.edu.pku.search.domain;

import java.io.Serializable;

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
	
	long employeeId;
	long recruitmentId;
	double relevance;
	
	public Relevance(){}

	public Relevance(long employeeId, long recruitmentId, double relevance) {
		super();
		this.employeeId = employeeId;
		this.recruitmentId = recruitmentId;
		this.relevance = relevance;
	}
	@Id
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	@Id
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (employeeId ^ (employeeId >>> 32));
		result = prime * result
				+ (int) (recruitmentId ^ (recruitmentId >>> 32));
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
		return true;
	}
	
	
}
