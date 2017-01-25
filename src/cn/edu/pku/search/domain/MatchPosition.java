package cn.edu.pku.search.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 匹配的招聘信息
 * @author lanzheng
 *
 */
public class MatchPosition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long employeeId;
	private double relevance;
	private AbstractPosition position;
	private List<PositionTag> positionTagList;
	
	public MatchPosition(long employeeId, double relevance,
			AbstractPosition position, List<PositionTag> positionTagList) {
		super();
		this.employeeId = employeeId;
		this.relevance = relevance;
		this.position = position;
		this.positionTagList = positionTagList;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public double getRelevance() {
		return relevance;
	}
	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}
	public AbstractPosition getPosition() {
		return position;
	}
	public void setPosition(AbstractPosition position) {
		this.position = position;
	}
	public List<PositionTag> getPositionTagList() {
		return positionTagList;
	}
	public void setPositionTagList(List<PositionTag> positionTagList) {
		this.positionTagList = positionTagList;
	}
	
	
}
