package cn.edu.pku.search.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.search.service.RecruitmentService;
import cn.edu.pku.search.service.ResumeService;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.service.EmployeeService;
import cn.edu.pku.util.FilePath;

/**
 * 与简历信息相关的控制器
 * @author Sun Xiaowei
 * 
 */
@Controller
@RequestMapping("resume")
public class ResumeController {

	public static final Logger logger = Logger
			.getLogger(ResumeController.class);

	private EmployeeService employeeService;
	private RecruitmentService recruitmentService;
	private ResumeService resumeService;

	public ResumeService getResumeService() {
		return resumeService;
	}

	@Resource
	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public RecruitmentService getRecruitmentService() {
		return recruitmentService;
	}

	@Resource
	public void setRecruitmentService(RecruitmentService recruitmentService) {
		this.recruitmentService = recruitmentService;
	}

	@RequestMapping(value = "addResume", method = RequestMethod.GET)
	public String addResume() {
		return "../WEB-INF/jsp/employee/addResume.jsp";
	}

	/**
	 * 添加简历基本信息
	 * 
	 * @param req
	 * @param res
	 * @return 进入下一步，添加教育信息页面
	 */
	@RequestMapping(value = "addResume", method = RequestMethod.POST)
	public String addResume(HttpServletRequest req, HttpServletResponse res) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile file = multipartRequest.getFile("photo");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = employeeId + "-" + format.format(new Date()) + "."
				+ file.getContentType().split("/")[1];
		try {
			file.transferTo(new File("../webapps/" + FilePath.photoPath, filename));
			String filepath = FilePath.photoPath + filename;
			String name = new String(req.getParameter("name").getBytes(
					"iso-8859-1"), "utf-8");
			String gender = new String(req.getParameter("gender").getBytes(
					"iso-8859-1"), "utf-8");
			String politics = new String(req.getParameter("politics").getBytes(
					"iso-8859-1"), "utf-8");
			String birthday = req.getParameter("birthday");
			int age = Integer.parseInt(req.getParameter("age"));
			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			String educationBackground = new String(req.getParameter(
					"educationBackground").getBytes("iso-8859-1"), "utf-8");
			String salary = req.getParameter("salary");
			String workingPlace = new String(req.getParameter("workingPlace")
					.getBytes("iso-8859-1"), "utf-8");
			String speciality = new String(req.getParameter("speciality")
					.getBytes("iso-8859-1"), "utf-8");
			String rewardAndPunishment = new String(req.getParameter(
					"rewardAndPunishment").getBytes("iso-8859-1"), "utf-8");
			String otherInfo = new String(req.getParameter("otherInfo")
					.getBytes("iso-8859-1"), "utf-8");
			String uploadTime = sFormat.format(new Date());
			String modifyTime = uploadTime;
			String uploadIp = req.getRequestURL().substring(7).split(":")[0];
			String modifyIp = uploadIp;
			Resume resume = resumeService.addResume(employeeId, name, gender,
					politics, birthday, age, email, phone,
					educationBackground, salary, workingPlace,
					filepath, speciality, rewardAndPunishment,
					otherInfo, uploadTime, modifyTime, uploadIp, modifyIp);
			session.setAttribute("resume", resume);

		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "../WEB-INF/jsp/employee/addEducation.jsp";
	}

	/**
	 * 跳转到添加教育经历信息的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addEducation", method = RequestMethod.GET)
	public String addEducation() {
		return "../WEB-INF/jsp/employee/addEducation.jsp";
	}

	/**
	 * 添加教育经历信息，可以在已有简历的情况下添加，也可以在没有简历的情况下创建
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "addEducation", method = RequestMethod.POST)
	public String addEducation(HttpServletRequest req, HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		String school = req.getParameter("school");
		String academy = req.getParameter("academy");
		String major = req.getParameter("major");
		String degree = req.getParameter("degree");
		String yearBeg = req.getParameter("yearBegin");
		String yearEnd = req.getParameter("yearEnd");
		String monthBeg = req.getParameter("monthBegin");
		String monthEnd = req.getParameter("monthEnd");
		String dateBegin = yearBeg + "-" + monthBeg + "-01";
		String dateEnd = yearEnd + "-" + monthEnd + "-01";
		String description = req.getParameter("description");
		resumeService.addEducation(employeeId, degree, academy, major, school,
				dateBegin, dateEnd, description);
		if (employee.getHasResume() == 1) {
			// 在已有简历的情况下添加教育信息
			return "checkResume";
		} else {
			// 在创建简历阶段添加教育信息
			return "../WEB-INF/jsp/employee/addEducation.jsp";
		}
	}

	/**
	 * 跳转到添加工作经历信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addWorkExperience", method = RequestMethod.GET)
	public String addWorkExperience() {
		return "../WEB-INF/jsp/employee/addWorkExperience.jsp";
	}

	/**
	 * 添加工作经历信息，可以在有简历的情况下添加，也可以在没有简历的情况下创建
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "addWorkExperience", method = RequestMethod.POST)
	public String addWorkExperience(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		String jobTitle = req.getParameter("jobTitle");
		String company = req.getParameter("company");
		String city = req.getParameter("city");
		int salary = Integer.parseInt(req.getParameter("salary"));
		String yearBeg = req.getParameter("yearBegin");
		String yearEnd = req.getParameter("yearEnd");
		String monthBeg = req.getParameter("monthBegin");
		String monthEnd = req.getParameter("monthEnd");
		String dateBegin = yearBeg + "-" + monthBeg + "-01";
		String dateEnd = yearEnd + "-" + monthEnd + "-01";
		String description = req.getParameter("description");
		resumeService.addWork(employeeId, jobTitle, company, city, salary,
				dateBegin, dateEnd, description);
		if (employee.getHasResume() == 1) {
			// 在已有简历的情况下添加工作信息
			return "checkResume";
		} else {
			// 在创建简历阶段添加工作信息并继续添加
			return "../WEB-INF/jsp/employee/addWorkExperience.jsp";
		}
	}

	/**
	 * 跳转到发布简历页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "publishResume", method = RequestMethod.GET)
	public String publishResume() {
		return "../WEB-INF/jsp/employee/publishResume.jsp";
	}

	/**
	 * 发布简历
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "publishResume", method = RequestMethod.POST)
	public String publishResume(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		employee = employeeService.uploadResume(employeeId);
		session.setAttribute("employee", employee);// 更新session
		return "checkResume";
	}

	/**
	 * 取消发布简历
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("cancelPublishResume")
	public String cancelPublishResume(HttpServletRequest req,
			HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		resumeService.cancelPublishResume(employeeId);
		return "../employee.jsp";
	}

	/**
	 * 查看简历
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("checkResume")
	public String checkResume(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		Resume resume = resumeService.checkResume(employeeId);
		List<Education> eduList = resumeService.checkEducation(employeeId);
		List<WorkExperience> workList = resumeService.checkWork(employeeId);
		session.setAttribute("resume", resume);
		session.setAttribute("eduList", eduList);
		session.setAttribute("workList", workList);
		return "../WEB-INF/jsp/employee/checkResume.jsp";
	}

	/**
	 * 删除工作经历信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteWork")
	public String deleteWork(HttpServletRequest req, HttpServletResponse res) {
		long workId = Long.parseLong(req.getParameter("workId"));
		resumeService.deleteWork(workId);
		return "checkResume";
	}

	/**
	 * 删除教育经历信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteEducation")
	public String deleteEducation(HttpServletRequest req,
			HttpServletResponse res) {
		long eduId = Long.parseLong(req.getParameter("eduId"));
		resumeService.deleteEducation(eduId);
		return "checkResume";
	}

	/**
	 * 跳转到更新简历信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateResume", method = RequestMethod.GET)
	public String updateResume() {
		return "../WEB-INF/jsp/employee/updateResume.jsp";
	}

	/**
	 * 更新简历
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateResume", method = RequestMethod.POST)
	public String updateResume(HttpServletRequest req, HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		try {
			String name = req.getParameter("name");
			String gender = req.getParameter("gender");
			String politics = req.getParameter("politics");
			String birthday = req.getParameter("birthday");
			int age = Integer.parseInt(req.getParameter("age"));
			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			String educationBackground = req
					.getParameter("educationBackground");
			String salary = req.getParameter("salary");
			String workingPlace = req.getParameter("workingPlace");
			String modifyTime = sFormat.format(new Date());
			String modifyIp = req.getRequestURL().substring(7).split(":")[0];
			Resume resume = resumeService.updateResume(employeeId, name,
					gender, politics, birthday, age, email, phone,
					educationBackground, salary, workingPlace, modifyTime,
					modifyIp);
			session.setAttribute("resume", resume);

		} catch (IllegalStateException e) {
			logger.error(e.getMessage(), e);
		}
		return "checkResume";
	}

	/**
	 * 跳转到更新教育经历信息页面
	 * 
	 * @param eduId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updateEducation", method = RequestMethod.GET)
	public String updateEducation(@RequestParam("eduId") long eduId,
			HttpServletRequest req) {
		Education education = resumeService.getEducation(eduId);
		HttpSession session = req.getSession();
		session.setAttribute("education", education);
		return "../WEB-INF/jsp/employee/updateEducation.jsp";
	}

	/**
	 * 更新教育经历信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateEducation", method = RequestMethod.POST)
	public String updateEducation(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		long id = Long.parseLong(req.getParameter("id"));
		long employeeId = Long.parseLong(req.getParameter("employeeId"));
		String school = req.getParameter("school");
		String academy = req.getParameter("academy");
		String major = req.getParameter("major");
		String degree = req.getParameter("degree");
		String yearBeg = req.getParameter("yearBegin");
		String yearEnd = req.getParameter("yearEnd");
		String monthBeg = req.getParameter("monthBegin");
		String monthEnd = req.getParameter("monthEnd");
		String dateBegin = yearBeg + "-" + monthBeg + "-01";
		String dateEnd = yearEnd + "-" + monthEnd + "-01";
		String description = req.getParameter("description");
		resumeService.updateEducation(id, employeeId, degree, academy, major,
				school, dateBegin, dateEnd, description);
		return "checkResume";
	}

	/**
	 * 跳转到更新工作经历信息页面
	 * 
	 * @param workId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updateWorkExperience", method = RequestMethod.GET)
	public String updateWorkExperience(@RequestParam("workId") long workId,
			HttpServletRequest req) {
		WorkExperience workExperience = resumeService.getWorkExperience(workId);
		HttpSession session = req.getSession();
		session.setAttribute("workExperience", workExperience);
		return "../WEB-INF/jsp/employee/updateWorkExperience.jsp";
	}

	/**
	 * 更新工作经历信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateWorkExperience", method = RequestMethod.POST)
	public String updateWorkExperience(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		long id = Long.parseLong(req.getParameter("id"));
		long employeeId = Long.parseLong(req.getParameter("employeeId"));
		String jobTitle = req.getParameter("jobTitle");
		String company = req.getParameter("company");
		String city = req.getParameter("city");
		int salary = Integer.parseInt(req.getParameter("salary"));
		String yearBeg = req.getParameter("yearBegin");
		String yearEnd = req.getParameter("yearEnd");
		String monthBeg = req.getParameter("monthBegin");
		String monthEnd = req.getParameter("monthEnd");
		String dateBegin = yearBeg + "-" + monthBeg + "-01";
		String dateEnd = yearEnd + "-" + monthEnd + "-01";
		String description = req.getParameter("description");
		resumeService.updateWorkExperience(id, employeeId, jobTitle, company,
				city, salary, dateBegin, dateEnd, description);
		return "checkResume";
	}

	/**
	 * 跳转到上传照片页面
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "changePhoto", method = RequestMethod.GET)
	public String changePhoto(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		Resume resume = resumeService.checkResume(employeeId);
		session.setAttribute("resume", resume);
		return "../WEB-INF/jsp/employee/changePhoto.jsp";
	}

	/**
	 * 上传照片
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "changePhoto", method = RequestMethod.POST)
	public String changePhoto(HttpServletRequest req, HttpServletResponse res) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile file = multipartRequest.getFile("photo");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename = employeeId + "-" + format.format(new Date()) + "."
				+ file.getContentType().split("/")[1];
		try {
			file.transferTo(new File("../webapps/" + FilePath.photoPath,
					filename));
			String filepath = FilePath.photoPath + filename;
			String modifyTime = sFormat.format(new Date());
			String modifyIp = req.getRequestURL().substring(7).split(":")[0];
			resumeService.changePhoto(employeeId, filepath, modifyTime,
					modifyIp);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "checkResume";
	}

	/**
	 * 跳转到更新专长页面
	 * @return
	 */
	@RequestMapping(value = "updateSpeciality", method = RequestMethod.GET)
	public String updateSpeciality() {
		return "../WEB-INF/jsp/employee/updateSpeciality.jsp";

	}

	/**
	 * 更新专长
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateSpeciality", method = RequestMethod.POST)
	public String updateSpeciality(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		String speciality = req.getParameter("speciality");
		resumeService.updateSpeciality(employeeId, speciality);
		return "checkResume";
	}

	/**
	 * 跳转到更新奖惩信息页面
	 * @return
	 */
	@RequestMapping(value = "updateReward", method = RequestMethod.GET)
	public String updateReward() {
		return "../WEB-INF/jsp/employee/updateReward.jsp";
	}

	/**
	 * 更新奖惩信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateReward", method = RequestMethod.POST)
	public String updateReward(HttpServletRequest req, HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		String rewardAndPunishment = req.getParameter("rewardAndPunishment");
		resumeService.updateReward(employeeId, rewardAndPunishment);
		return "checkResume";
	}

	/**
	 * 跳转到更新其他信息页面
	 * @return
	 */
	@RequestMapping(value = "updateOtherInfo", method = RequestMethod.GET)
	public String updateOtherInfo() {
		return "../WEB-INF/jsp/employee/updateOtherInfo.jsp";
	}

	/**
	 * 更新其他信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "updateOtherInfo", method = RequestMethod.POST)
	public String updateOtherInfo(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		String otherInfo = req.getParameter("otherInfo");
		resumeService.updateOtherInfo(employeeId, otherInfo);
		return "checkResume";
	}
	
	/**
	 * 查看招聘信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("checkRecruitment")
	public String checkRecruitment(HttpServletRequest req,
			HttpServletResponse res) {
		long id = Long.parseLong(req.getParameter("recruitId"));
		Recruitment recruit = recruitmentService.getRecruitment(id);
//		List<Attachment> listAttach = recruitmentService.listAttachment(recruit
//				.getId());
		HttpSession session = req.getSession();
		session.setAttribute("recruitment", recruit);
//		session.setAttribute("listAttach", listAttach);
		return "../WEB-INF/jsp/employee/checkRecruitment.jsp";
	}

}
