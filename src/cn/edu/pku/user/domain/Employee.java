package cn.edu.pku.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employee")
public class Employee {
	long id;
	String email;
	String password;
	int active;
	int hasResume;
	int logins;
	
	int subscriptionNum;
	int recFreq;
	int hasTag;

	public Employee() {	}
	
	public Employee(String email, String password, int active, int hasResume) {
		super();
		this.email = email;
		this.password = password;
		this.active = active;
		this.hasResume = hasResume;
		this.logins = 0;
	}
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="has_resume")
	public int getHasResume() {
		return hasResume;
	}
	public void setHasResume(int hasResume) {
		this.hasResume = hasResume;
	}
	
	public int getLogins() {
		return logins;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}
	
	@Column(name="subscription_num")
	public int getSubscriptionNum() {
		return subscriptionNum;
	}

	public void setSubscriptionNum(int subscriptionNum) {
		this.subscriptionNum = subscriptionNum;
	}

	@Column(name="rec_freq")
	public int getRecFreq() {
		return recFreq;
	}

	public void setRecFreq(int recFreq) {
		this.recFreq = recFreq;
	}

	@Column(name="has_tag")
	public int getHasTag() {
		return hasTag;
	}

	public void setHasTag(int hasTag) {
		this.hasTag = hasTag;
	}
	
	
}
