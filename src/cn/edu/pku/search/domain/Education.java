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
@Table(name="education")
public class Education implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long employeeId;
	private String degree;
	private String academy;
	private String major;
	private String school;
	private String dateBegin;
	private String dateEnd;
	private String description;
	
	public Education() {}
	
	public Education(long employeeId, String degree, String academy,
			String major, String school, String dateBegin, String dateEnd,
			String description) {
		super();
		this.employeeId = employeeId;
		this.degree = degree;
		this.academy = academy;
		this.major = major;
		this.school = school;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.description = description;
	}
	
	public Education(long id, long employeeId, String degree, String academy,
			String major, String school, String dateBegin, String dateEnd,
			String description) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.degree = degree;
		this.academy = academy;
		this.major = major;
		this.school = school;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.description = description;
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
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	@Column(name="date_begin")
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	
	@Column(name="date_end")
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
