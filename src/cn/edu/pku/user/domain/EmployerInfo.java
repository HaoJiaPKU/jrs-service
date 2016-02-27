package cn.edu.pku.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employerinfo")
public class EmployerInfo {

	long id;
	long employerId;
	String companyName;
	String province;
	String city;
	String linkman;
	String gender;
	String phone;
	
	
	public EmployerInfo(long employerId, String companyName, String province,
			String city, String linkman, String gender, String phone) {
		super();
		this.employerId = employerId;
		this.companyName = companyName;
		this.province = province;
		this.city = city;
		this.linkman = linkman;
		this.gender = gender;
		this.phone = phone;
	}
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="employer_id")
	public long getEmployerId() {
		return employerId;
	}
	public void setEmployerId(long employerId) {
		this.employerId = employerId;
	}
	
	@Column(name="company_name")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
