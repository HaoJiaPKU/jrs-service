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
@Table(name="workexperience")
public class WorkExperience implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private long employeeId;
	private String jobTitle;
	private String company;
	private String city;
	private int salary;
	private String dateBegin;
	private String dateEnd;
	private String description;
	
	public WorkExperience() {}
	
	public String textField() {
		return jobTitle
			+ " " + company
			+ " " + city
			+ " " + description;		
	}
	
	public WorkExperience(long employeeId, String jobTitle, String company,
			String city, int salary, String dateBegin, String dateEnd,
			String description) {
		super();
		this.employeeId = employeeId;
		this.jobTitle = jobTitle;
		this.company = company;
		this.city = city;
		this.salary = salary;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.description = description;
	}
	
	public WorkExperience(long id, long employeeId, String jobTitle,
			String company, String city, int salary, String dateBegin,
			String dateEnd, String description) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.jobTitle = jobTitle;
		this.company = company;
		this.city = city;
		this.salary = salary;
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
	@Column(name="job_title")
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
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
