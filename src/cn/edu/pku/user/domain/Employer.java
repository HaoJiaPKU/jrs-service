package cn.edu.pku.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employer")
public class Employer {

	long id;
	String email;
	String password;
	int active;
	
	public Employer() {}
	
	public Employer(String email, String password, int active) {
		super();
		this.email = email;
		this.password = password;
		this.active = active;
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
	
	
}
