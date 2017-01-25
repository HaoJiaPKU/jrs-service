package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lanzheng
 *
 */
@Entity
@Table(name="attachment")
public class Attachment implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private long positionId;
	private String filepath;
	
	public Attachment() {}
	
	public Attachment(long positionId, String filepath) {
		super();
		this.positionId = positionId;
		this.filepath = filepath;
	}
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="position_id")
	public long getPositionId() {
		return positionId;
	}
	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
	
}
