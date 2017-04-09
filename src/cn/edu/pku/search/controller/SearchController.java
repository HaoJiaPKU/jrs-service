package cn.edu.pku.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.pku.search.domain.AbstractPosition;
import cn.edu.pku.search.domain.AbstractResume;
import cn.edu.pku.search.domain.MatchResume;
import cn.edu.pku.search.domain.Pager;
import cn.edu.pku.search.domain.MatchPosition;
import cn.edu.pku.search.service.PositionService;
import cn.edu.pku.search.service.SearchService;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.user.service.EmployeeService;

/**
 * 与搜索和匹配相关的控制器
 * @author lanzheng
 *
 */
@Controller
@RequestMapping("search")
public class SearchController {

	public static final Logger logger = Logger.getLogger(SearchController.class);
	
	private SearchService searchService;
	private PositionService positionService;

	EmployeeService employeeService;

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public SearchService getSearchService() {
		return searchService;
	}

	@Resource
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public PositionService getPositionService() {
		return positionService;
	}

	@Resource
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	/**
	 * 搜索招聘信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("searchPosition")
	public String searchPosition(HttpServletRequest req,HttpServletResponse res) {
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
		
		Pager<AbstractPosition> searchResult
			= searchService.searchPosition("content", key,offset);
		
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
		return "../WEB-INF/jsp/employee/listMatchPosition.jsp";
	}
	
	/**
	 * 为企业更新简历与招聘信息的匹配相关度
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("updateRelevanceForEmployer")
	public String updateRelevanceForEmployer(HttpServletRequest req, HttpServletResponse res) {
		long positionId = Long.parseLong(req.getParameter("positionId"));
		new Thread(new updateRelevanceForEmployer(positionId)).start();
		return "../employer.jsp";
	}
	
	/**
	 * 列出匹配的招聘信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("listMatchPosition")
	public String listMatchPosition(HttpServletRequest req,
			HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		long employeeId = employee.getId();
		List<EmployeeTag> employeeTagList = employeeService.listEmployeeTag(employee.getId());
		session.setAttribute("employeeTagList", employeeTagList);
		int offset = Integer.parseInt(req.getParameter("offset"));
		Pager<MatchPosition> relevancePager = searchService.listMatchPosition(employeeId, offset);
		session.removeAttribute("relevancePager");
		session.setAttribute("relevancePager", relevancePager);
		return "../WEB-INF/jsp/employee/listMatchPosition.jsp";
	}
	
	@RequestMapping("listMatchResume")
	public String listMatchResume(HttpServletRequest req, HttpServletResponse res) {
		long positionId = Long.parseLong(req.getParameter("positionId"));
		int offset = Integer.parseInt(req.getParameter("offset"));
		Pager<MatchResume> relevancePager = searchService.listMatchResume(positionId, offset);
		HttpSession session = req.getSession();
		session.removeAttribute("relevancePager");
		session.setAttribute("relevancePager", relevancePager);
		return "../WEB-INF/jsp/employer/listMatchResume.jsp?positionId="+positionId;
	}

	/**
	 * 更新相关度的线程
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
		
		long positionId;
		
		public updateRelevanceForEmployer(long positionId) {
			this.positionId = positionId;
		}

		@Override
		public void run() {
			searchService.updateRelevanceForEmployer(positionId);
		}
	}
	
	
	
}
