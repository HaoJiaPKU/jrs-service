package cn.edu.pku.search.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.Resume;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.search.service.RecruitmentService;
import cn.edu.pku.search.service.ResumeService;
import cn.edu.pku.user.domain.Employer;
import cn.edu.pku.util.FilePath;

/**
 * 与招聘信息相关的控制器
 * 
 * @author Sun Xiaowei
 * 
 */
@Controller
@RequestMapping("recruitment")
public class RecruitmentController {

	public static final Logger logger = Logger
			.getLogger(RecruitmentController.class);

	private RecruitmentService recruitmentService;
	private ResumeService resumeService;

	public ResumeService getResumeService() {
		return resumeService;
	}

	@Resource
	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	public RecruitmentService getRecruitmentService() {
		return recruitmentService;
	}

	@Resource
	public void setRecruitmentService(RecruitmentService recruitmentService) {
		this.recruitmentService = recruitmentService;
	}

	/**
	 * 跳转到添加招聘信息的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addRecruitment", method = RequestMethod.GET)
	public String addRecruitment() {
		return "../WEB-INF/jsp/employer/addRecruitment.jsp";
	}

	/**
	 * 添加招聘信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "addRecruitment", method = RequestMethod.POST)
	public String addRecruitment(HttpServletRequest req, HttpServletResponse res) {
		try {
			SimpleDateFormat sFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			long employerId = Long.parseLong(req.getParameter("employerId"));
			String title = new String(req.getParameter("title").getBytes(
					"iso-8859-1"), "utf-8");
			String degree = new String(req.getParameter("degree").getBytes(
					"iso-8859-1"), "utf-8");
			String city = new String(req.getParameter("city").getBytes(
					"iso-8859-1"), "utf-8");
			String company = new String(req.getParameter("company").getBytes(
					"iso-8859-1"), "utf-8");
			String position = new String(req.getParameter("position").getBytes(
					"iso-8859-1"), "utf-8");
			String business = new String(req.getParameter("business").getBytes(
					"iso-8859-1"), "utf-8");
			String scale = new String(req.getParameter("scale").getBytes(
					"iso-8859-1"), "utf-8");
			String type = new String(req.getParameter("type").getBytes(
					"iso-8859-1"), "utf-8");
			String salary = new String(req.getParameter("salary").getBytes(
					"iso-8859-1"), "utf-8");
			int recruitNum = Integer.parseInt(req.getParameter("recruitNum"));
			String description = new String(req.getParameter("description")
					.getBytes("iso-8859-1"), "utf-8");

			int attach = Integer.parseInt(req.getParameter("attachNum"));
			String uploadTime = sFormat.format(new Date());
			String modifyTime = uploadTime;
			String uploadIp = req.getRequestURL().substring(7).split(":")[0];
			String modifyIp = uploadIp;
			long recruitmentId = recruitmentService.addRecruitment(employerId,
					uploadTime, modifyTime, uploadIp, modifyIp, title, degree,
					city, company, position, business, scale, type, salary,
					recruitNum, description);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			for (int i = 0; i <= attach; i++) {
				MultipartFile file = multipartRequest.getFile("attachment" + i);
				if (!file.isEmpty()) {
					String extension = file.getContentType().split("/")[1];
					String filename = recruitmentId + "-" + i + "." + extension;
					recruitmentService.addAttachment(recruitmentId,
							FilePath.attachmentPath + filename);
					file.transferTo(new File(FilePath.attachmentPath, filename));
				}
			}
		} catch (IllegalStateException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "listRecruitment";
	}

	/**
	 * 列出某一用户发布的招聘信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("listRecruitment")
	public String listRecruiment(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employer employer = (Employer) session.getAttribute("employer");
		long employerId = employer.getId();
		List<Recruitment> list = recruitmentService.listRecruitment(employerId);
		session.setAttribute("listRecruitment", list);
		return "../WEB-INF/jsp/employer/listRecruitment.jsp";
	}

	/**
	 * 查看某一条招聘信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("checkRecruitment")
	public String checkRecruitment(HttpServletRequest req,
			HttpServletResponse res) {
		long id = Long.parseLong(req.getParameter("id"));
		Recruitment recruit = recruitmentService.getRecruitment(id);
//		List<Attachment> listAttach = recruitmentService.listAttachment(recruit
//				.getId());
		HttpSession session = req.getSession();
		session.setAttribute("recruitment", recruit);
//		session.setAttribute("listAttach", listAttach);
		return "../WEB-INF/jsp/employer/checkRecruitment.jsp";
	}

	/**
	 * 删除某一条招聘信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteRecruitment")
	public String deleteRecruitment(HttpServletRequest req,
			HttpServletResponse res) {
		long id = Long.parseLong(req.getParameter("id"));
		recruitmentService.deleteRecruitment(id);
		return "listRecruitment";
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
		long employeeId = Long.parseLong(req.getParameter("employeeId"));
		Resume resume = resumeService.checkResume(employeeId);
		List<Education> eduList = resumeService.checkEducation(employeeId);
		List<WorkExperience> workList = resumeService.checkWork(employeeId);
		session.setAttribute("resume", resume);
		session.setAttribute("eduList", eduList);
		session.setAttribute("workList", workList);
		return "../WEB-INF/jsp/employer/checkResume.jsp";
	}
}
