package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lanzheng
 *
 */
@Entity
@Table(name="relevance")
public class Relevance implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private long resumeId;
	private int positionSource;
	private long positionId;
	private double relevance;
	
	public Relevance(){}


	public Relevance(long employeeId, long resumeId, int positionSource,
			long positionId, double relevance) {
		super();
		this.employeeId = employeeId;
		this.resumeId = resumeId;
		this.positionSource = positionSource;
		this.positionId = positionId;
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
	@Column(name="position_id")
	public long getPositionId() {
		return positionId;
	}
	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}
	
	@Column(name="relevance")
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
	@Column(name="position_source")
	public int getPositionSource() {
		return positionSource;
	}

	public void setPositionSource(int positionSource) {
		this.positionSource = positionSource;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (employeeId ^ (employeeId >>> 32));
		result = prime * result
				+ (int) (positionId ^ (positionId >>> 32));
		result = prime * result + positionSource;
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
		if (positionId != other.positionId)
			return false;
		if (positionSource != other.positionSource)
			return false;
		if (resumeId != other.resumeId)
			return false;
		return true;
	}


}
