package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name="recruitment_bbs")
public class RecruitmentBBS extends AbstractRecruitment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String url;
	private String title;
	private String time;
	private String source;
	private String content;
	private String custom;
	private String snapshotUrl;
	private int hasTag;
	
	public RecruitmentBBS() {}
	
	public RecruitmentBBS(String url, String title, String time, String source,
			String content, String custom, String snapshotUrl) {
		super();
		this.url = url;
		this.title = title;
		this.time = time;
		this.source = source;
		this.content = content;
		this.custom = custom;
		this.snapshotUrl = snapshotUrl;
	}

	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	@Column(name = "snapshot_url")
	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}
	
	@Column(name = "has_tag")
	public int getHasTag() {
		return hasTag;
	}

	public void setHasTag(int hasTag) {
		this.hasTag = hasTag;
	}

	@Override
	public String toString() {
		return title + "\n" + content;
	}
	
}
