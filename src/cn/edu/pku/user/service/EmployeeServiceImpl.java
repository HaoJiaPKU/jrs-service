package cn.edu.pku.user.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.pku.user.dao.EmployeeDAO;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.util.Config;
import cn.edu.pku.util.Encrypt;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	public static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
	
	private EmployeeDAO employeeDao;
	private JavaMailSenderImpl javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	private TaskExecutor taskExecutor;

	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}

	@Resource
	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	@Resource
	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	@Resource
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Transactional
	@Override
	public Employee login(String email, String password) {
		password = Encrypt.encrypt(password);
		Employee employee = employeeDao.load(email);
		if (employee != null && password.equals(employee.getPassword()) && employee.getActive() == 1) {
			employee.setLogins(employee.getLogins() + 1);
			employeeDao.update(employee);
			return employee;
		}
		return null;
	}

	@Transactional
	@Override
	public Employee regist(String email, String password) {
		password = Encrypt.encrypt(password);
		Employee employee = employeeDao.load(email);
		if (employee != null) {
			return null;
		}
		employee = new Employee(email, password, 0, 0);
		employeeDao.add(employee);
		return employee;
	}

	@Override
	public void sendVerificationEmail(Employee employee) {
		String content = "激活地址： http://" + Config.domainName
				+ "employee/verification?id=" + employee.getId() + "&password="
				+ employee.getPassword();
		simpleMailMessage.setSubject("jobPKU账号激活");
		simpleMailMessage.setTo(employee.getEmail());
		simpleMailMessage.setText(content);
		taskExecutor.execute(new Runnable() {
			public void run() {
				javaMailSender.send(simpleMailMessage);
			}
		});
	}
	
	@Override
	public void sendVerificationAgain(long id) {
		Employee employee = employeeDao.load(id);
		this.sendVerificationEmail(employee);
	}
	
	@Transactional
	@Override
	public Employee activate(long id, String password) {
		Employee employee = employeeDao.load(id);
		if(employee.getPassword().equals(password)) {
			employee.setActive(1);
			employeeDao.update(employee);
			return employee;
		}
		return null;
	}

	@Transactional
	@Override
	public Employee uploadResume(long id) {
		Employee employee = employeeDao.load(id);
		employee.setHasResume(1);
		employeeDao.update(employee);
		return employee;
	}
	
	

}
