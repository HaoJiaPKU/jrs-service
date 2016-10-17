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
		EmployeeTag other = (EmployeeTag) obj;
		if (employeeId != other.employeeId)
			return false;
		if (tagName != other.tagName)
			return false;
		if (tagValue != other.tagValue)
			return false;
		return true;
	}
}
