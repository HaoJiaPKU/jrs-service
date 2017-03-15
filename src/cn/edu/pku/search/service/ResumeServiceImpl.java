package cn.edu.pku.search.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.pku.search.dao.ResumeDAO;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.ResumeJobpopo;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.user.dao.EmployeeDAO;

@Service
public class ResumeServiceImpl implements ResumeService {

	public static final Logger logger = Logger.getLogger(ResumeServiceImpl.class);
	
	public ResumeDAO resumeDao;
	public EmployeeDAO employeeDao;

	public ResumeDAO getResumeDao() {
		return resumeDao;
	}

	@Resource
	public void setResumeDao(ResumeDAO resumeDao) {
		this.resumeDao = resumeDao;
	}
	
	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}

	@Resource
	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Transactional
	@Override
	public ResumeJobpopo addResume(long employeeId, String name, String gender,
			String politics, String birthday, int age,
			String email, String phone, String educationBackground,
			String salary, String workingPlace,
			String photo, String speciality,
			String rewardAndPunishment, String otherInfo, String uploadTime,
			String modifyTime, String uploadIp, String modifyIp,
			String industryIntension, String categoryIntension) {
		ResumeJobpopo resume = new ResumeJobpopo(employeeId, name, gender, politics,
				"", birthday, age, email, phone, educationBackground,
				"", salary, workingPlace, photo,
				speciality, rewardAndPunishment, otherInfo, uploadTime,
				modifyTime, uploadIp, modifyIp,
				industryIntension, categoryIntension);
		resumeDao.addResume(resume);
		return resume;
	}

	@Override
	public Education addEducation(long employeeId, String degree, String academy,
			String major, String school, String dateBegin, String dateEnd,
			String description) {
		Education education = new Education(employeeId, degree, academy, major,
				school, dateBegin, dateEnd, description);
		resumeDao.addEducation(education);
		return education;
	}

	@Override
	public ResumeJobpopo checkResume(long employeeId) {
		return resumeDao.getResume(employeeId);
	}

	@Override
	public List<Education> checkEducation(long employeeId) {
		return resumeDao.listEducation(employeeId);
	}

	@Override
	public WorkExperience addWork(long employeeId, String jobTitle, String company,
			String city, int salary, String dateBegin, String dateEnd,
			String description) {
		WorkExperience work = new WorkExperience(employeeId, jobTitle, company,
				city, salary, dateBegin, dateEnd, description);
		resumeDao.addWorkExperience(work);
		return work;
	}

	@Override
	public List<WorkExperience> checkWork(long employeeId) {
		return resumeDao.listWorkExperience(employeeId);
	}

	@Override
	public void deleteWork(long id) {
		resumeDao.deleteWorkExperience(id);
	}

	@Override
	public void deleteEducation(long id) {
		resumeDao.deleteEducation(id);

	}

	@Override
	public Education getEducation(long id) {
		return resumeDao.getEducation(id);
	}

	@Override
	public WorkExperience getWorkExperience(long id) {
		return resumeDao.getWorkExperience(id);
	}

	@Override
	public ResumeJobpopo updateResume(long employeeId, String name, String gender,
			String politics, String birthday, int age,
			String email, String phone, String educationBackground,
			String salary, String workingPlace,
			String modifyTime, String modifyIp,
			String industryIntension, String categoryIntension) {
		ResumeJobpopo resume = resumeDao.getResume(employeeId);
		resume.setName(name);
		resume.setGender(gender);
		resume.setPolitics(politics);
		resume.setBirthday(birthday);
		resume.setAge(age);
		resume.setEmail(email);
		resume.setPhone(phone);
		resume.setEducationBackground(educationBackground);
		resume.setSalary(salary);
		resume.setWorkingPlace(workingPlace);
		resume.setModifyIp(modifyIp);
		resume.setModifyTime(modifyTime);
		resume.setIndustryIntension(industryIntension);
		resume.setCategoryIntension(categoryIntension);
		resumeDao.updateResume(resume);
		return resume;

	}

	@Override
	public void updateEducation(long id, long employeeId, String degree,
			String academy, String major, String school, String dateBegin,
			String dateEnd, String description) {
		Education education = new Education(id, employeeId, degree, academy,
				major, school, dateBegin, dateEnd, description);
		resumeDao.updateEducation(education);

	}

	@Override
	public void updateWorkExperience(long id, long employeeId, String jobTitle,
			String company, String city, int salary, String dateBegin,
			String dateEnd, String description) {
		WorkExperience workExperience = new WorkExperience(id, employeeId,
				jobTitle, company, city, salary, dateBegin, dateEnd,
				description);
		resumeDao.updateWorkExperience(workExperience);
	}

	@Transactional
	@Override
	public void cancelPublishResume(long employeeId) {
		resumeDao.deleteEducationByEmployeeId(employeeId);
		resumeDao.deleteWorkExperienceByEmployeeId(employeeId);
		resumeDao.deleteResume(employeeId);
		
	}

	@Transactional
	@Override
	public ResumeJobpopo changePhoto(long employeeId, String photo, String modifyTime,
			String modifyIp) {
		ResumeJobpopo resume = resumeDao.getResume(employeeId);
		resume.setPhoto(photo);
		resume.setModifyIp(modifyIp);
		resume.setModifyTime(modifyTime);
		resumeDao.updateResume(resume);
		return resume;
	}

	@Override
	public ResumeJobpopo updateSpeciality(long employeeId, String speciality) {
		ResumeJobpopo resume = resumeDao.getResume(employeeId);
		resume.setSpeciality(speciality);
		resumeDao.updateResume(resume);
		return resume;
	}

	@Override
	public ResumeJobpopo updateReward(long employeeId, String rewardAndPunishment) {
		ResumeJobpopo resume = resumeDao.getResume(employeeId);
		resume.setRewardAndPunishment(rewardAndPunishment);
		resumeDao.updateResume(resume);
		return resume;
	}

	@Override
	public ResumeJobpopo updateOtherInfo(long employeeId, String otherInfo) {
		ResumeJobpopo resume = resumeDao.getResume(employeeId);
		resume.setOtherInfo(otherInfo);
		resumeDao.updateResume(resume);
		return resume;
	}

}
