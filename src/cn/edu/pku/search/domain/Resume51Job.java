package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Sun Xiaowei
 *
 */
@Entity
@Table(name="resume_51job")
public class Resume51Job extends AbstractResume implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	long id;
	String path;
	String content;
	
	public Resume51Job(){}
	
	public Resume51Job(long id, String path, String content) {
		super();
		this.id = id;
		this.path = path;
		this.content = content;
	}
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
