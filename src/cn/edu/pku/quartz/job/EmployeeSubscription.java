package cn.edu.pku.quartz.job;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.pku.search.dao.PositionDAO;
import cn.edu.pku.search.dao.RelevanceDAO;
import cn.edu.pku.search.service.SearchService;

/**
 * Created by Lan Zheng on 4/7/16.
 * email: lanzheng@pku.edu.cn
 */

@Service
public class EmployeeSubscription implements Job {
	
	private JavaMailSenderImpl javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	private TaskExecutor taskExecutor;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		javaMailSender = (JavaMailSenderImpl) jobDataMap.get("javaMailSender");
		simpleMailMessage = (SimpleMailMessage) jobDataMap.get("simpleMailMessage");
		taskExecutor = (TaskExecutor) jobDataMap.get("taskExecutor");
		
		String content = jobDataMap.getString("content");
		String email = jobDataMap.getString("email");
		
		simpleMailMessage.setSubject("jobpopo职位推荐");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setText(content);
		taskExecutor.execute(new Runnable() {
			public void run() {
				javaMailSender.send(simpleMailMessage);
			}
		});
	}

	public JavaMailSenderImpl getJavaMailSender() {
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
}