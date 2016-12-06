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
@Table(name = "recruitment")
public class Recruitment extends AbstractRecruitment implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long employerId;
	private String uploadTime;
	private String modifyTime;
	private String uploadIp;
	private String modifyIp;
	private String title;
	private String degree;// 学历
	private String city;// 工作地点
	private String company;// 公司名称
	private String position;// 职位名称
	private String business;// 公司行业
	private String scale;// 公司规模
	private String type;// 公司类型 合资/独资/民营/外企。。。
	private String salary;// 薪资
	private int recruitNum;// 招聘人数
	private String description;// 职位描述

	public Recruitment() {
	}
	
	public String textField() {
		return this.title
			+ " " + this.description;
	}

	public Recruitment(long employerId, String uploadTime, String modifyTime,
			String uploadIp, String modifyIp, String title, String degree, String city,
			String company, String position, String business, String scale,
			String type, String salary, int recruitNum, String description) {
		this.employerId = employerId;
		this.uploadTime = uploadTime;
		this.modifyTime = modifyTime;
		this.uploadIp = uploadIp;
		this.modifyIp = modifyIp;
		this.title = title;
		this.degree = degree;
		this.city = city;
		this.company = company;
		this.position = position;
		this.business = business;
		this.scale = scale;
		this.type = type;
		this.salary = salary;
		this.recruitNum = recruitNum;
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

	@Column(name = "employer_id")
	public long getEmployerId() {
		return employerId;
	}

	public void setEmployerId(long employerId) {
		this.employerId = employerId;
	}

	@Column(name = "upload_time")
	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "modify_time")
	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "upload_ip")
	public String getUploadIp() {
		return uploadIp;
	}

	public void setUploadIp(String uploadIp) {
		this.uploadIp = uploadIp;
	}

	@Column(name = "modify_ip")
	public String getModifyIp() {
		return modifyIp;
	}

	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getRecruitNum() {
		return recruitNum;
	}

	public void setRecruitNum(int recruitNum) {
		this.recruitNum = recruitNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
