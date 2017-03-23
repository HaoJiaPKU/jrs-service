package cn.edu.pku.user.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
//import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
//import org.apache.lucene.util.packed.PackedLongValues.Iterator;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.pku.quartz.factory.ScheduleFactory;
import cn.edu.pku.quartz.job.EmployeeSubscription;
import cn.edu.pku.search.dao.IndustryDAO;
import cn.edu.pku.search.dao.PositionDAO;
import cn.edu.pku.search.dao.RelevanceDAO;
import cn.edu.pku.search.domain.PositionJobpopo;
import cn.edu.pku.search.domain.Industry;
import cn.edu.pku.search.domain.Position;
import cn.edu.pku.search.domain.Relevance;
import cn.edu.pku.search.service.SearchService;
import cn.edu.pku.user.dao.EmployeeDAO;
import cn.edu.pku.user.dao.EmployeeTagDAO;
import cn.edu.pku.user.domain.Employee;
import cn.edu.pku.user.domain.EmployeeTag;
import cn.edu.pku.util.Config;
import cn.edu.pku.util.Encrypt;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	public static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
	
	private EmployeeDAO employeeDAO;
	private PositionDAO positionDAO;
	private RelevanceDAO relevanceDAO;
	private EmployeeTagDAO employeeTagDAO;
	private IndustryDAO industryDAO;
	
	private JavaMailSenderImpl javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	private TaskExecutor taskExecutor;
	
	private SearchService searchService;

	
	
	public IndustryDAO getIndustryDAO() {
		return industryDAO;
	}

	@Resource
	public void setIndustryDAO(IndustryDAO industryDAO) {
		this.industryDAO = industryDAO;
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	@Resource
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
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
		Employee employee = employeeDAO.load(email);
		if (employee != null && password.equals(employee.getPassword()) && employee.getActive() == 1) {
			employee.setLogins(employee.getLogins() + 1);
			employeeDAO.update(employee);
			return employee;
		}
		return null;
	}

	@Transactional
	@Override
	public Employee regist(String email, String password) {
		password = Encrypt.encrypt(password);
		Employee employee = employeeDAO.load(email);
		if (employee != null) {
			return null;
		}
		employee = new Employee(email, password, 0, 0);
		employeeDAO.add(employee);
		return employee;
	}

	@Override
	public void sendVerificationEmail(Employee employee) {
		String content = "激活地址： http://" + Config.domainName
				+ "employee/verification?id=" + employee.getId() + "&password="
				+ employee.getPassword();
		simpleMailMessage.setSubject("jobpopo账号激活");
		simpleMailMessage.setTo(employee.getEmail());
		simpleMailMessage.setText(content);
		
		logger.warn("\n\n" + content);
		
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					javaMailSender.send(simpleMailMessage);
				}
				catch (Exception e) {
					logger.error("\n\n" + e.getMessage());
				}
			}
		});
	}
	
	@Override
	public void sendVerificationAgain(long id) {
		Employee employee = employeeDAO.load(id);
		this.sendVerificationEmail(employee);
	}
	
	@Transactional
	@Override
	public Employee activate(long id, String password) {
		Employee employee = employeeDAO.load(id);
		if(employee.getPassword().equals(password)) {
			employee.setActive(1);
			employeeDAO.update(employee);
			return employee;
		}
		return null;
	}

	@Transactional
	@Override
	public Employee uploadResume(long id) {
		Employee employee = employeeDAO.load(id);
		employee.setHasResume(1);
		employeeDAO.update(employee);
		return employee;
	}
	
	@Transactional
	@Override
	public Employee updateSubscription(
			long id, int subscriptionNum, int recFreqDay, int recFreqHour) {
		Employee employee = employeeDAO.load(id);
		employee.setSubscriptionNum(subscriptionNum);
		employee.setRecFreqDay(recFreqDay);
		employee.setRecFreqHour(recFreqHour);
		employeeDAO.update(employee);
		
		updateSubscriptionService(
				id, employee.getEmail(), subscriptionNum, recFreqDay, recFreqHour);
		
		return employee;
	}
	
	@SuppressWarnings("unchecked")
	public void updateSubscriptionService(
			long id, String email, int subscriptionNum, int recFreqDay, int recFreqHour) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(
				     "employee" + String.valueOf(id), "employee");
			
			Trigger trigger = scheduler.getTrigger(triggerKey);
			
			String cronExp = "0 0 " + String.valueOf(recFreqHour);
			if (recFreqDay == 0) {
				cronExp += " * * " + "?";
			} else if (recFreqDay == 1) {
				cronExp += " ? * " + "Mon";
			} else if (recFreqDay == 2) {
				cronExp += " ? * " + "TUE";
			} else if (recFreqDay == 3) {
				cronExp += " ? * " + "WED";
			} else if (recFreqDay == 4) {
				cronExp += " ? * " + "THU";
			} else if (recFreqDay == 5) {
				cronExp += " ? * " + "FRI";
			} else if (recFreqDay == 6) {
				cronExp += " ? * " + "SAT";
			} else if (recFreqDay == 7) {
				cronExp += " ? * " + "SUN";
			}
			
			System.out.println(cronExp);
			
			if(trigger == null) {	
				JobDetail jobDetail = newJob(EmployeeSubscription.class)
						.withIdentity("employee" + String.valueOf(id), "employee")
						.usingJobData("email", email)
						.build();
				jobDetail.getJobDataMap().put("javaMailSender", javaMailSender);
				jobDetail.getJobDataMap().put("simpleMailMessage", simpleMailMessage);
				jobDetail.getJobDataMap().put("taskExecutor", taskExecutor);
				
				String content = getSubscriptionContent(id, subscriptionNum);
				jobDetail.getJobDataMap().put("content", content);
				
				logger.warn("\n\n" + content);
				
				trigger = newTrigger()
						.withIdentity("employee" + String.valueOf(id), "employee")
						.startNow()
						.withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
						.build();
				
				scheduler.scheduleJob(jobDetail, trigger);
			}
			else {
				
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(triggerKey)
						.startNow()
//						.withSchedule(ScheduleFactory.getSimpleSchedule(recFreq))
						.withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
						.build();
				scheduler.rescheduleJob(triggerKey, trigger);
			}			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public List<EmployeeTag> listEmployeeTag(long employeeId) {
		return employeeTagDAO.listEmployeeTag(employeeId);
	}
	
	public String getSubscriptionContent(long id, int subscriptionNum) {
		String content = new String();
		List<Relevance> list = relevanceDAO.listRelevanceForEmployee(id);
		for(int i = 0; i < 50 && i < subscriptionNum; i ++) {
			if(list.get(i).getPositionSource() == 1) {
				PositionJobpopo rec = positionDAO.loadPosition(list.get(i).getPositionId());
				rec.setDescription(rec.getDescription().substring(0, 100)+"...");//为了前台只显示两三行内容
				content += "\n\n"
						+ rec.getCompany()
						+ " 相关度：" + String.valueOf(
								(double)((int) (100000 * list.get(i).getRelevance())) / 1000.0) + "%\n"
						+ "链接：" + rec.getModifyIp();
			} else if(list.get(i).getPositionSource() == 2) {
				Position rec = positionDAO.loadPositionBbs(list.get(i).getPositionId());
				content += "\n\n"
						+ rec.getPosTitle()
						+ " 相关度：" + String.valueOf(
								(double)((int) (100000 * list.get(i).getRelevance())) / 1000.0) + "%\n"
						+ "链接：" + rec.getPosUrl();
			}
		}
		return content;
	}
	
	public SearchService getSearchService() {
		return searchService;
	}

	@Resource
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	@Resource
	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public RelevanceDAO getRelevanceDAO() {
		return relevanceDAO;
	}

	@Resource
	public void setRelevanceDAO(RelevanceDAO relevanceDAO) {
		this.relevanceDAO = relevanceDAO;
	}

	public EmployeeTagDAO getEmployeeTagDAO() {
		return employeeTagDAO;
	}

	@Resource
	public void setEmployeeTagDAO(EmployeeTagDAO employeeTagDAO) {
		this.employeeTagDAO = employeeTagDAO;
	}

	@Transactional
	@Override
	public HashMap<String, Integer> loadAllIndustry() {
		// TODO Auto-generated method stub
		List<Industry> list  = industryDAO.loadAllIndustry();
		HashMap<String, Integer> ret
			= new HashMap<String, Integer>();
		int counter = 0;
		for (Iterator<Industry> it = list.iterator(); it.hasNext(); ) {
			Industry ind = it.next();
			String industry = ind.getIndustry();
			int num = ind.getNum();
			ret.put(industry, num);
			if (++ counter >= 10) {
				break;
			}
		}
		return ret;
	}
	
}
