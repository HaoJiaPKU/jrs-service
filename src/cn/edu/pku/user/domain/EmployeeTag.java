/**
 * 
 */
package cn.edu.pku.user.domain;

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
@Table(name="employee_tag")
public class EmployeeTag implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private String tagName;
	private double tagValue;
	
	public EmployeeTag () {}
	
	public EmployeeTag (long employeeId, String tagName, double tagValue) {
		super();
		this.employeeId = employeeId;
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	@Id
	@Column(name="employee_id")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (employeeId ^ (employeeId >>> 32));
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tagValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		EmployeeTag other = (EmployeeTag) obj;
		if (employeeId != other.employeeId)
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		if (Double.doubleToLongBits(tagValue) != Double.doubleToLongBits(other.tagValue))
			return false;
		return true;
	}

	
}
