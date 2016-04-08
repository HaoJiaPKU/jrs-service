package cn.edu.pku.search.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.pku.search.domain.AbstractRecruitment;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Pager;
import cn.edu.pku.search.domain.MatchRecruitment;
import cn.edu.pku.search.service.RecruitmentService;
import cn.edu.pku.search.service.SearchService;
import cn.edu.pku.user.domain.Employee;

/**
 * 与搜索和匹配相关的控制器
 * @author Sun Xiaowei
 *
 */
@Controller
@RequestMapping("search")
public class SearchController {

	public static final Logger logger = Logger.getLogger(SearchController.class);
	
	private SearchService searchService;
	private RecruitmentService recruitmentService;

	public SearchService getSearchService() {
		return searchService;
	}

	@Resource
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public RecruitmentService getRecruitmentService() {
		return recruitmentService;
	}

	@Resource
	public void setRecruitmentService(RecruitmentService recruitmentService) {
		this.recruitmentService = recruitmentService;
	}

	/**
	 * 搜索招聘信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("searchRecruitment")
	public String searchRecruitment(HttpServletRequest req,HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		String key = req.getParameter("key");
		int offset = Integer.parseInt(req.getParameter("offset"));
		if((key == null || key.equals(""))) {
			return "../employee.jsp";
		}
		
		Pager<AbstractRecruitment> searchResult = searchService.searchRecruitment("content", key,offset);
		
		req.setAttribute("searchResult", searchResult);
		req.setAttribute("key", key);
		return "../WEB-INF/jsp/employee/searchResult.jsp?key=" + key;
	}
	
	/**
	 * 搜索简历信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("searchResume")
	public String searchResume(HttpServletRequest req,HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		String key = req.getParameter("key");
		int offset = Integer.parseInt(req.getParameter("offset"));
		Pager<AbstractResume> searchResult;
		if(key == null || key.equals("")) {
			return "../employer.jsp";
		}
		searchResult = searchService.searchResume("contents", key, offset);
		req.setAttribute("searchResult", searchResult);
		req.setAttribute("key", key);
		return "../WEB-INF/jsp/employer/searchResult.jsp?key=" + key;
	}
	
	/**
	 * 为求职者更新简历与招聘信息的匹配相关度
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("updateRelevanceForEmployee")
	public String updateRelevanceForEmployee(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		new Thread(new updateRelevanceForEmployee(employeeId)).start();
		return "../employee.jsp";
	}
	
	/**
	 * 为企业更新简历与招聘信息的匹配相关度
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("updateRelevanceForEmployer")
	public String updateRelevanceForEmployer(HttpServletRequest req, HttpServletResponse res) {
		long recruitmentId = Long.parseLong(req.getParameter("recruitmentId"));
		new Thread(new updateRelevanceForEmployer(recruitmentId)).start();
		return "../employer.jsp";
	}
	
	/**
	 * 列出匹配的招聘信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("listMatchRecruitment")
	public String listMatchRecruitment(HttpServletRequest req,
			HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		int offset = Integer.parseInt(req.getParameter("offset"));
		Pager<MatchRecruitment> relevancePager = searchService.listMatchRecruitment(employeeId, offset);
		session.removeAttribute("relevancePager");
		session.setAttribute("relevancePager", relevancePager);
		return "../WEB-INF/jsp/employee/listMatchRecruitment.jsp";
	}
	
	@RequestMapping("listMatchResume")
	public String listMatchResume(HttpServletRequest req, HttpServletResponse res) {
		long recruitmentId = Long.parseLong(req.getParameter("recruitmentId"));
		int offset = Integer.parseInt(req.getParameter("offset"));
		Pager<MatchResume> relevancePager = searchService.listMatchResume(recruitmentId, offset);
		HttpSession session = req.getSession();
		session.removeAttribute("relevancePager");
		session.setAttribute("relevancePager", relevancePager);
		return "../WEB-INF/jsp/employer/listMatchResume.jsp?recruitmentId="+recruitmentId;
	}

	/**
	 * 更新相关度的线程
	 * @author Sun Xiaowei
	 *
	 */
	private class updateRelevanceForEmployee implements Runnable {
		
		long employeeId;
		
		public updateRelevanceForEmployee(long employeeId) {
			this.employeeId = employeeId;
		}

		@Override
		public void run() {
			searchService.updateRelevanceForEmployee(employeeId);
		}
	}
	
	private class updateRelevanceForEmployer implements Runnable {
		
		long recruitmentId;
		
		public updateRelevanceForEmployer(long recruitmentId) {
			this.recruitmentId = recruitmentId;
		}

		@Override
		public void run() {
			searchService.updateRelevanceForEmployer(recruitmentId);
		}
	}
	
	
	
}
