package cn.edu.pku.quartz.factory;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

public class HelloJob implements Job {

	private JavaMailSenderImpl javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	
	  @Override
	  public void execute(JobExecutionContext context) throws JobExecutionException {

			String content = "";
			String email = "479609281@qq.com";
			simpleMailMessage = new SimpleMailMessage();
			javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setHost("smtp.163.com");
			javaMailSender.setPort(465);
			javaMailSender.setUsername("sekesei@163.com");
			javaMailSender.setPassword("seke-20130329");
//			taskExecutor = new ThreadPoolTaskExecutor(); 
			
			simpleMailMessage.setSubject("jobpopo职位推荐");
			simpleMailMessage.setTo(email);
			simpleMailMessage.setText(content);
			System.out.println("send");
			javaMailSender.send(simpleMailMessage);
//			taskExecutor.execute(new Runnable() {
//				public void run() {
//					javaMailSender.send(simpleMailMessage);
//				}
//			});

	   }

	public JavaMailSenderImpl getJavaMailSender() {
		return javaMailSender;
	}
	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	  
	  
}