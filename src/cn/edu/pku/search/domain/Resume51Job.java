package cn.edu.pku.search.domain;

import java.io.Serializable;

/**
 * 
 * @author Sun Xiaowei
 *
 */
public class Resume51Job extends AbstractResume implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String path;
	String content;
	
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
