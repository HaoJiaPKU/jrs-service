package cn.edu.pku.search.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Lan Zheng
 *
 */
@Entity
@Table(name="recruitment_v1")
public class Position extends AbstractPosition implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String posTitle;
	private String posSalary;
	private String posLocation;
	private String posPublishDate;
	private String posType;
	private String posExperience;
	private String posDegree;
	private String posRecruitNum;
	private String posCategory;
	private String posDescription;
	private String posUrl;
	
	private String comScale;
	private String comType;
	private String comIndustry;
	private String comHost;
	private String comLocation;
	
	private int hasTag = 0;	
	private String source;
	private String snapshotUrl;
	private String displayContent;
	
	public Position() {
		
	}

	public String textField() {
		return this.posTitle + "\n" + this.posDescription;
	}
	
	public Position(long id, String posTitle, String posSalary, String posLocation, String posPublishDate,
			String posType, String posExperience, String posDegree, String posRecruitNum, String posCategory,
			String posDescription, String posUrl, String comScale, String comType, String comIndustry, String comHost,
			String comLocation, int hasTag, String source, String snapshotUrl, String displayContent) {
		super();
		this.id = id;
		this.posTitle = posTitle;
		this.posSalary = posSalary;
		this.posLocation = posLocation;
		this.posPublishDate = posPublishDate;
		this.posType = posType;
		this.posExperience = posExperience;
		this.posDegree = posDegree;
		this.posRecruitNum = posRecruitNum;
		this.posCategory = posCategory;
		this.posDescription = posDescription;
		this.posUrl = posUrl;
		this.comScale = comScale;
		this.comType = comType;
		this.comIndustry = comIndustry;
		this.comHost = comHost;
		this.comLocation = comLocation;
		this.hasTag = hasTag;
		this.source = source;
		this.snapshotUrl = snapshotUrl;
		this.displayContent = displayContent;
	}

	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "pos_title")
	public String getPosTitle() {
		return posTitle;
	}

	public void setPosTitle(String posTitle) {
		this.posTitle = posTitle;
	}

	@Column(name = "pos_salary")
	public String getPosSalary() {
		return posSalary;
	}

	public void setPosSalary(String posSalary) {
		this.posSalary = posSalary;
	}

	@Column(name = "pos_location")
	public String getPosLocation() {
		return posLocation;
	}

	public void setPosLocation(String posLocation) {
		this.posLocation = posLocation;
	}

	@Column(name = "pos_publish_date")
	public String getPosPublishDate() {
		return posPublishDate;
	}

	public void setPosPublishDate(String posPublishDate) {
		this.posPublishDate = posPublishDate;
	}

	@Column(name = "pos_type")
	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType;
	}

	@Column(name = "pos_experience")
	public String getPosExperience() {
		return posExperience;
	}

	public void setPosExperience(String posExperience) {
		this.posExperience = posExperience;
	}

	@Column(name = "pos_degree")
	public String getPosDegree() {
		return posDegree;
	}

	public void setPosDegree(String posDegree) {
		this.posDegree = posDegree;
	}

	@Column(name = "pos_recruit_num")
	public String getPosRecruitNum() {
		return posRecruitNum;
	}

	public void setPosRecruitNum(String posRecruitNum) {
		this.posRecruitNum = posRecruitNum;
	}

	@Column(name = "pos_category")
	public String getPosCategory() {
		return posCategory;
	}

	public void setPosCategory(String posCategory) {
		this.posCategory = posCategory;
	}

	@Column(name = "pos_description")
	public String getPosDescription() {
		return posDescription;
	}

	public void setPosDescription(String posDescription) {
		this.posDescription = posDescription;
	}

	@Column(name = "pos_url")
	public String getPosUrl() {
		return posUrl;
	}

	public void setPosUrl(String posUrl) {
		this.posUrl = posUrl;
	}

	@Column(name = "com_scale")
	public String getComScale() {
		return comScale;
	}

	public void setComScale(String comScale) {
		this.comScale = comScale;
	}

	@Column(name = "com_type")
	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	@Column(name = "com_industry")
	public String getComIndustry() {
		return comIndustry;
	}

	public void setComIndustry(String comIndustry) {
		this.comIndustry = comIndustry;
	}

	@Column(name = "com_host")
	public String getComHost() {
		return comHost;
	}

	public void setComHost(String comHost) {
		this.comHost = comHost;
	}

	@Column(name = "com_location")
	public String getComLocation() {
		return comLocation;
	}

	public void setComLocation(String comLocation) {
		this.comLocation = comLocation;
	}

	@Column(name = "has_tag")
	public int getHasTag() {
		return hasTag;
	}

	public void setHasTag(int hasTag) {
		this.hasTag = hasTag;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "snapshot_url")
	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	@Column(name = "display_content")
	public String getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comHost == null) ? 0 : comHost.hashCode());
		result = prime * result + ((comIndustry == null) ? 0 : comIndustry.hashCode());
		result = prime * result + ((comLocation == null) ? 0 : comLocation.hashCode());
		result = prime * result + ((comScale == null) ? 0 : comScale.hashCode());
		result = prime * result + ((comType == null) ? 0 : comType.hashCode());
		result = prime * result + ((displayContent == null) ? 0 : displayContent.hashCode());
		result = prime * result + hasTag;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((posCategory == null) ? 0 : posCategory.hashCode());
		result = prime * result + ((posDescription == null) ? 0 : posDescription.hashCode());
		result = prime * result + ((posDegree == null) ? 0 : posDegree.hashCode());
		result = prime * result + ((posExperience == null) ? 0 : posExperience.hashCode());
		result = prime * result + ((posLocation == null) ? 0 : posLocation.hashCode());
		result = prime * result + ((posPublishDate == null) ? 0 : posPublishDate.hashCode());
		result = prime * result + ((posRecruitNum == null) ? 0 : posRecruitNum.hashCode());
		result = prime * result + ((posSalary == null) ? 0 : posSalary.hashCode());
		result = prime * result + ((posType == null) ? 0 : posType.hashCode());
		result = prime * result + ((posUrl == null) ? 0 : posUrl.hashCode());
		result = prime * result + ((posTitle == null) ? 0 : posTitle.hashCode());
		result = prime * result + ((snapshotUrl == null) ? 0 : snapshotUrl.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (comHost == null) {
			if (other.comHost != null)
				return false;
		} else if (!comHost.equals(other.comHost))
			return false;
		if (comIndustry == null) {
			if (other.comIndustry != null)
				return false;
		} else if (!comIndustry.equals(other.comIndustry))
			return false;
		if (comLocation == null) {
			if (other.comLocation != null)
				return false;
		} else if (!comLocation.equals(other.comLocation))
			return false;
		if (comScale == null) {
			if (other.comScale != null)
				return false;
		} else if (!comScale.equals(other.comScale))
			return false;
		if (comType == null) {
			if (other.comType != null)
				return false;
		} else if (!comType.equals(other.comType))
			return false;
		if (displayContent == null) {
			if (other.displayContent != null)
				return false;
		} else if (!displayContent.equals(other.displayContent))
			return false;
		if (hasTag != other.hasTag)
			return false;
		if (id != other.id)
			return false;
		if (posCategory == null) {
			if (other.posCategory != null)
				return false;
		} else if (!posCategory.equals(other.posCategory))
			return false;
		if (posDescription == null) {
			if (other.posDescription != null)
				return false;
		} else if (!posDescription.equals(other.posDescription))
			return false;
		if (posDegree == null) {
			if (other.posDegree != null)
				return false;
		} else if (!posDegree.equals(other.posDegree))
			return false;
		if (posExperience == null) {
			if (other.posExperience != null)
				return false;
		} else if (!posExperience.equals(other.posExperience))
			return false;
		if (posLocation == null) {
			if (other.posLocation != null)
				return false;
		} else if (!posLocation.equals(other.posLocation))
			return false;
		if (posPublishDate == null) {
			if (other.posPublishDate != null)
				return false;
		} else if (!posPublishDate.equals(other.posPublishDate))
			return false;
		if (posRecruitNum == null) {
			if (other.posRecruitNum != null)
				return false;
		} else if (!posRecruitNum.equals(other.posRecruitNum))
			return false;
		if (posSalary == null) {
			if (other.posSalary != null)
				return false;
		} else if (!posSalary.equals(other.posSalary))
			return false;
		if (posType == null) {
			if (other.posType != null)
				return false;
		} else if (!posType.equals(other.posType))
			return false;
		if (posUrl == null) {
			if (other.posUrl != null)
				return false;
		} else if (!posUrl.equals(other.posUrl))
			return false;
		if (posTitle == null) {
			if (other.posTitle != null)
				return false;
		} else if (!posTitle.equals(other.posTitle))
			return false;
		if (snapshotUrl == null) {
			if (other.snapshotUrl != null)
				return false;
		} else if (!snapshotUrl.equals(other.snapshotUrl))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
}