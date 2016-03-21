package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	
	long id;
	long employeeId;
	long resumeId;
	int recruitmentSource;
	long recruitmentId;
	double relevance;
	
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


	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="employee_id")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
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
	@Column(name="resume_id")
	public long getResumeId() {
		return resumeId;
	}

	public void setResumeId(long resumeId) {
		this.resumeId = resumeId;
	}
	@Column(name="recruitment_source")
	public int getRecruitmentSource() {
		return recruitmentSource;
	}

	public void setRecruitmentSource(int recruitmentSource) {
		this.recruitmentSource = recruitmentSource;
	}


}
