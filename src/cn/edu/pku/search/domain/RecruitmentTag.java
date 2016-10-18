/**
 * 
 */
package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lanzheng
 *
 */

@Entity
@Table(name="recruitment_tag")
public class RecruitmentTag implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long recruitmentId;
	private String tagName;
	private double tagValue;
	
	public RecruitmentTag () {}
	
	public RecruitmentTag (long recruitmentId, String tagName, double tagValue) {
		super();
		this.recruitmentId = recruitmentId;
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	@Id
	@Column(name="recruitment_id")
	public long getRecruitmentId() {
		return recruitmentId;
	}
	public void setRecruitmentId(long recruitmentId) {
		this.recruitmentId = recruitmentId;
	}
	
	@Id
	@Column(name="tag_name")
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Column(name="tag_value")
	public double getTagValue() {
		return tagValue;
	}
	public void setTagValue(double tagValue) {
		this.tagValue = tagValue;
	}

	@Override
	// TODO
	public int hashCode() {
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecruitmentTag other = (RecruitmentTag) obj;
		if (recruitmentId != other.recruitmentId)
			return false;
		if (tagName != other.tagName)
			return false;
		if (tagValue != other.tagValue)
			return false;
		return true;
	}
}
