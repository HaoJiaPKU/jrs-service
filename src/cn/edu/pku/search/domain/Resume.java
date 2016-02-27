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
@Table(name="resume")
public class Resume extends AbstractResume implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private String name;
	private String gender;
	private String politics;
	private String nativePlace;
	private String birthday;
	private int age;
	private String email;
	private String phone;
	private String educationBackground;
	private String workingYears;
	private String salary;
	private String workingPlace;
	private String jobIntension;
	private String photo;
	private String speciality;
	private String rewardAndPunishment;
	private String otherInfo;
	private String uploadTime;
	private String modifyTime;
	private String uploadIp;
	private String modifyIp;
	
	public Resume() {
		
	}
	
	public Resume(long employeeId, String name, String gender, String politics,
			String nativePlace, String birthday, int age, String email,
			String phone, String educationBackground, String workingYears,
			String salary, String workingPlace, String jobIntension, String photo,
			String speciality, String rewardAndPunishment, String otherInfo,
			String uploadTime, String modifyTime, String uploadIp,
			String modifyIp) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.gender = gender;
		this.politics = politics;
		this.nativePlace = nativePlace;
		this.birthday = birthday;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.educationBackground = educationBackground;
		this.workingYears = workingYears;
		this.salary = salary;
		this.workingPlace = workingPlace;
		this.jobIntension = jobIntension;
		this.photo = photo;
		this.speciality = speciality;
		this.rewardAndPunishment = rewardAndPunishment;
		this.otherInfo = otherInfo;
		this.uploadTime = uploadTime;
		this.modifyTime = modifyTime;
		this.uploadIp = uploadIp;
		this.modifyIp = modifyIp;
	}
	@Id
	@Column(name="employee_id")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPolitics() {
		return politics;
	}
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	@Column(name="native_place")
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="edu_background")
	public String getEducationBackground() {
		return educationBackground;
	}
	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}
	@Column(name="working_years")
	public String getWorkingYears() {
		return workingYears;
	}
	public void setWorkingYears(String workingYears) {
		this.workingYears = workingYears;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	@Column(name="working_place")
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	@Column(name="job_intension")
	public String getJobIntension() {
		return jobIntension;
	}
	public void setJobIntension(String jobIntension) {
		this.jobIntension = jobIntension;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	@Column(name="reward_and_punishment")
	public String getRewardAndPunishment() {
		return rewardAndPunishment;
	}
	public void setRewardAndPunishment(String rewardAndPunishment) {
		this.rewardAndPunishment = rewardAndPunishment;
	}
	@Column(name="other_info")
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	@Column(name="upload_time")
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Column(name="modify_time")
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Column(name="upload_ip")
	public String getUploadIp() {
		return uploadIp;
	}
	public void setUploadIp(String uploadIp) {
		this.uploadIp = uploadIp;
	}
	@Column(name="modify_ip")
	public String getModifyIp() {
		return modifyIp;
	}
	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
	}
	
	
}
