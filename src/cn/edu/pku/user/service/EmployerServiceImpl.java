package cn.edu.pku.user.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.pku.user.dao.EmployerDAO;
import cn.edu.pku.user.dao.EmployerInfoDAO;
import cn.edu.pku.user.domain.Employer;
import cn.edu.pku.user.domain.EmployerInfo;
import cn.edu.pku.util.Config;
import cn.edu.pku.util.Encrypt;

@Service
public class EmployerServiceImpl implements EmployerService{
	
	public static final Logger logger = Logger.getLogger(EmployerServiceImpl.class);
	
	EmployerDAO employerDao;
	EmployerInfoDAO employerInfoDao;
	private JavaMailSenderImpl javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	private TaskExecutor taskExecutor;
	
	public EmployerDAO getEmployerDao() {
		return employerDao;
	}

	@Resource
	public void setEmployerDao(EmployerDAO employerDao) {
		this.employerDao = employerDao;
	}

	public EmployerInfoDAO getEmployerInfoDao() {
		return employerInfoDao;
	}

	@Resource
	public void setEmployerInfoDao(EmployerInfoDAO employerInfoDao) {
		this.employerInfoDao = employerInfoDao;
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


	@Override
	public Employer login(String email, String password) {
		password = Encrypt.encrypt(password);
		Employer employer = employerDao.loadByUsername(email);
		if(employer != null && password.equals(employer.getPassword()) && employer.getActive() == 1)
			return employer;
		return null;
	}

	@Transactional
	@Override
	public Employer regist(String companyName, String province, String city,
			String linkman, String gender, String phone, String email,
			String password) {
		password = Encrypt.encrypt(password);
		Employer employer = employerDao.loadByUsername(email);
		if(employer != null) {
			return null;
		}
		employer = new Employer(email, password, 0);
		long id = employerDao.add(employer);
		EmployerInfo employerInfo = new EmployerInfo(id, companyName, province, city, linkman, gender, phone);
		employerInfoDao.add(employerInfo);
		return employer;
		
	}

	@Override
	public void sendVerificationEmail(Employer employer) {
		String content = "激活地址： http://" + Config.domainName
				+ "employer/verification?id=" + employer.getId() + "&password="
				+ employer.getPassword();
		simpleMailMessage.setSubject("jobPKU账号激活");
		simpleMailMessage.setTo(employer.getEmail());
		simpleMailMessage.setText(content);
		taskExecutor.execute(new Runnable() {
			public void run() {
				javaMailSender.send(simpleMailMessage);
			}
		});
	}

	@Transactional
	@Override
	public Employer activate(long id, String password) {
		Employer employer = employerDao.load(id);
		if(employer.getPassword().equals(password)) {
			employer.setActive(1);
			employerDao.update(employer);
			return employer;
		}
		return null;
	}


}
