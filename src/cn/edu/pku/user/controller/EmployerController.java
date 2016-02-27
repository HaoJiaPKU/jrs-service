package cn.edu.pku.user.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.pku.user.domain.Employer;
import cn.edu.pku.user.service.EmployerService;

/**
 * 与企业相关的控制器
 * @author Sun Xiaowei
 *
 */
@Controller
@RequestMapping("employer")
public class EmployerController {
	
	public static final Logger logger = Logger.getLogger(EmployerController.class);
	
	EmployerService employerService;
	
	public EmployerService getEmployerService() {
		return employerService;
	}

	@Resource
	public void setEmployerService(EmployerService employerService) {
		this.employerService = employerService;
	}

	/**
	 * 登录
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest req,HttpServletResponse res) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Employer employer = employerService.login(email, password);
		HttpSession session = req.getSession();
		if(employer != null) {
			session.setAttribute("employer", employer);
			session.setMaxInactiveInterval(3600*24);
			return "../employer.jsp";
		}
		session.setAttribute("message", "用户名或密码错误");
		return "../message.jsp";
	}
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="regist",method=RequestMethod.GET)
	public String regist(){
		return "../WEB-INF/jsp/employer/regist.jsp";
	}
	
	/**
	 * 注册
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="regist",method=RequestMethod.POST)
	public String regist(HttpServletRequest req,HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		String companyName = req.getParameter("companyname");
		String province = req.getParameter("province");
		String city = req.getParameter("city");
		String linkman = req.getParameter("linkman");
		String gender = req.getParameter("gender");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Employer employer = employerService.regist(companyName, province, city, linkman, gender, phone, email, password);
		
		HttpSession session = req.getSession();
		if(employer != null) {
			employerService.sendVerificationEmail(employer);
			session.setAttribute("message", "验证邮件已发送");
			return "../message.jsp";
		}
		session.setAttribute("message", "邮箱已被注册");
		return "../message.jsp";
	}
	
	/**
	 * 注销
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest req,HttpServletResponse res) {
		HttpSession session = req.getSession();
		session.removeAttribute("employee");
		session.removeAttribute("employer");
		return "../employer.jsp";
	}
	
	/**
	 * 账户激活
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("verification")
	public String verification(HttpServletRequest req,HttpServletResponse res) {
		long id = Long.parseLong(req.getParameter("id"));
		String password = req.getParameter("password");
		Employer employer = employerService.activate(id, password);
		HttpSession session = req.getSession();
		if(employer != null) {
			session.setAttribute("employer", employer);
			return "../employer.jsp";
		}
		session.setAttribute("message", "验证失败");
		return "../message.jsp";
	}
}
