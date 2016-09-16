package cn.edu.pku.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.service.EmployeeService;

/**
 * 与求职者相关的控制器
 * @author Sun Xiaowei, Lan Zheng
 *
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {
	
	public static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	EmployeeService employeeService;

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 登录
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest req,HttpServletResponse res) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Employee employee = employeeService.login(username, password);
		HttpSession session = req.getSession();
		if(employee != null) {
			session.setAttribute("employee", employee);
			session.setMaxInactiveInterval(3600*24);
			if(employee.getLogins() == 1)
				return "../guideEmployee.jsp";
			return "../loginWelcome.jsp";
		}
		session.setAttribute("message", "用户名或密码错误");
		return "../message.jsp";
	}
	
	/**
	 * 跳转到注册界面
	 * @return
	 */
	@RequestMapping(value="regist",method=RequestMethod.GET)
	public String regist(){
		return "../WEB-INF/jsp/employee/regist.jsp";
	}
	
	/**
	 * 注册
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="regist",method=RequestMethod.POST)
	public String regist(HttpServletRequest req,HttpServletResponse res) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		Employee employee = employeeService.regist(email, password);
		HttpSession session = req.getSession();
		if(employee != null) {
			employeeService.sendVerificationEmail(employee);//发送认证邮件
			session.setAttribute("VerificationEmployeeId", employee.getId());
			return "../WEB-INF/jsp/employee/sendVerificationEmail.jsp";
		}
		session.setAttribute("message", "邮箱已被注册");
		return "../message.jsp";
	}
	
	/**
	 * 再次发送认证邮件
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="sendVerificationAgain",method=RequestMethod.GET)
	public String sendVerificationAgain(HttpServletRequest req,HttpServletResponse res) {
		long id = Long.parseLong(req.getParameter("id"));
		employeeService.sendVerificationAgain(id);
		HttpSession session = req.getSession();
		session.setAttribute("VerificationEmployeeId", id);
		return "../WEB-INF/jsp/employee/sendVerificationEmail.jsp";
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
		return "../employee.jsp";
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
		Employee employee = employeeService.activate(id, password);
		HttpSession session = req.getSession();
		if(employee != null) {
			session.setAttribute("employee", employee);
			return "../employee.jsp";
		}
		session.setAttribute("message", "验证失败");
		return "../message.jsp";
	}

	/**
	 * 显示邮件订阅信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("check")
	public String check(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		session.setAttribute("subscriptionNum", employee.getSubscriptionNum());
		session.setAttribute("recFreq", employee.getRecFreq());
		return "../WEB-INF/jsp/employee/subscription.jsp";
	}

	/**
	 * 更新邮件订阅信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest req, HttpServletResponse res) {
		int subscriptionNum = Integer.parseInt(req.getParameter("subscriptionNum"));
		int recFreq = Integer.parseInt(req.getParameter("recFreq"));
		long id = Long.parseLong(req.getParameter("employeeId"));
		Employee employee = employeeService.updateSubscription(id, subscriptionNum, recFreq);
		HttpSession session = req.getSession();
		session.setAttribute("subscriptionNum", employee.getSubscriptionNum());
		session.setAttribute("recFreq", employee.getRecFreq());
		return "../WEB-INF/jsp/employee/subscription.jsp";
	}
}
