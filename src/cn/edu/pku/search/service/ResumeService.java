package cn.edu.pku.search.service;

import java.util.List;

import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.WorkExperience;

public interface ResumeService {

	/**
	 * 添加简历信息
	 * 
	 * @param employeeId
	 *            用户ID
	 * @param name
	 *            姓名
	 * @param gender
	 *            性别
	 * @param politics
	 *            政治面貌
	 * @param nativePlace
	 *            籍贯
	 * @param birthday
	 *            生日
	 * @param age
	 *            年龄
	 * @param email
	 *            邮箱
	 * @param phone
	 *            手机号
	 * @param educationBackground
	 *            学历
	 * @param workingYears
	 *            工作年限
	 * @param salary
	 *            薪水
	 * @param workingPlace
	 *            工作地点
	 * @param jobIntension
	 *            求职意向
	 * @param photo
	 *            照片地址
	 * @param speciality
	 *            专长
	 * @param rewardAndPunishment
	 *            奖惩信息
	 * @param otherInfo
	 *            其他信息
	 * @param uploadTime
	 *            上传时间
	 * @param modifyTime
	 *            修改时间
	 * @param uploadIp
	 *            上传IP
	 * @param modifyIp
	 *            修改IP
	 * @return
	 */
	public Resume addResume(long employeeId, String name, String gender,
			String politics, String birthday, int age, String email,
			String phone, String educationBackground, String salary,
			String workingPlace, String photo, String speciality,
			String rewardAndPunishment, String otherInfo, String uploadTime,
			String modifyTime, String uploadIp, String modifyIp);

	/**
	 * 根据用户ID查看简历
	 * 
	 * @param employeeId
	 *            用户ID
	 * @return
	 */
	public Resume checkResume(long employeeId);

	/**
	 * 添加教育经历
	 * 
	 * @param employeeId
	 *            用户ID
	 * @param degree
	 *            学历
	 * @param academy
	 *            学院
	 * @param major
	 *            专业
	 * @param school
	 *            学校
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            结束时间
	 * @param description
	 *            经历描述
	 * @return
	 */
	public Education addEducation(long employeeId, String degree,
			String academy, String major, String school, String dateBegin,
			String dateEnd, String description);

	/**
	 * 查看教育经历信息
	 * 
	 * @param employeeId
	 * @return
	 */
	public List<Education> checkEducation(long employeeId);

	/**
	 * 添加工作经历
	 * 
	 * @param employeeId
	 *            用户ID
	 * @param jobTitle
	 *            职位名称
	 * @param company
	 *            公司名称
	 * @param city
	 *            城市
	 * @param salary
	 *            薪水
	 * @param dateBegin
	 *            开始时间
	 * @param dateEnd
	 *            结束时间
	 * @param description
	 *            经历描述
	 */
	public WorkExperience addWork(long employeeId, String jobTitle,
			String company, String city, int salary, String dateBegin,
			String dateEnd, String description);

	/**
	 * 查看工作经历信息
	 * 
	 * @param employeeId
	 * @return
	 */
	public List<WorkExperience> checkWork(long employeeId);

	/**
	 * 删除工作经历
	 * 
	 * @param id
	 */
	public void deleteWork(long id);

	/**
	 * 删除教育经历
	 * 
	 * @param id
	 */
	public void deleteEducation(long id);

	/**
	 * 得到某一条教育经历信息
	 * 
	 * @param id
	 * @return
	 */
	public Education getEducation(long id);

	/**
	 * 得到某一条工作经历信息
	 * 
	 * @param id
	 * @return
	 */
	public WorkExperience getWorkExperience(long id);

	/**
	 * 更新简历信息
	 * 
	 * @param employeeId
	 * @param name
	 * @param gender
	 * @param politics
	 * @param nativePlace
	 * @param birthday
	 * @param age
	 * @param email
	 * @param phone
	 * @param educationBackground
	 * @param workingYears
	 * @param salary
	 * @param workingPlace
	 * @param jobIntension
	 * @param photo
	 * @param speciality
	 * @param rewardAndPunishment
	 * @param otherInfo
	 * @param uploadTime
	 * @param modifyTime
	 * @param uploadIp
	 * @param modifyIp
	 */
	public Resume updateResume(long employeeId, String name, String gender,
			String politics, String birthday, int age, String email,
			String phone, String educationBackground, String salary,
			String workingPlace, String modifyTime, String modifyIp);

	/**
	 * 更新教育经历信息
	 * 
	 * @param id
	 * @param employeeId
	 * @param degree
	 * @param academy
	 * @param major
	 * @param school
	 * @param dateBegin
	 * @param dateEnd
	 * @param description
	 */
	public void updateEducation(long id, long employeeId, String degree,
			String academy, String major, String school, String dateBegin,
			String dateEnd, String description);

	/**
	 * 更新工作经历信息
	 * 
	 * @param id
	 * @param employeeId
	 * @param jobTitle
	 * @param company
	 * @param city
	 * @param salary
	 * @param dateBegin
	 * @param dateEnd
	 * @param description
	 */
	public void updateWorkExperience(long id, long employeeId, String jobTitle,
			String company, String city, int salary, String dateBegin,
			String dateEnd, String description);

	/**
	 * 取消发布简历
	 * 
	 * @param employeeId
	 */
	public void cancelPublishResume(long employeeId);

	/**
	 * 修改头像
	 * 
	 * @param employeeId
	 * @param photo
	 * @param modifyTime
	 * @param modifyIp
	 * @return
	 */
	public Resume changePhoto(long employeeId, String photo, String modifyTime,
			String modifyIp);

	/**
	 * 更新专长信息
	 * 
	 * @param employeeId
	 * @param speciality
	 * @return
	 */
	public Resume updateSpeciality(long employeeId, String speciality);

	/**
	 * 更新奖惩信息
	 * 
	 * @param employeeId
	 * @param rewardAndPunishment
	 * @return
	 */
	public Resume updateReward(long employeeId, String rewardAndPunishment);

	/**
	 * 更新其他信息
	 * 
	 * @param employee
	 * @param otherInfo
	 * @return
	 */
	public Resume updateOtherInfo(long employeeId, String otherInfo);

}
