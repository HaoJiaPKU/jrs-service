package cn.edu.pku.search.service;

import java.util.List;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.RecruitmentBBS;

public interface RecruitmentService {

	/**
	 * 添加招聘信息
	 * @param employerId
	 * @param uploadTime
	 * @param modifyTime
	 * @param uploadIp
	 * @param modifyIp
	 * @param title
	 * @param degree
	 * @param city
	 * @param company
	 * @param position
	 * @param business
	 * @param scale
	 * @param type
	 * @param salary
	 * @param recruitNum
	 * @param description
	 * @return
	 */
	public long addRecruitment(long employerId, String uploadTime, String modifyTime,
			String uploadIp, String modifyIp, String title, String degree, String city,
			String company, String position, String business, String scale,
			String type, String salary, int recruitNum, String description);
	
	/**
	 * 添加附件
	 * @param recruitmentId
	 * @param filepath
	 */
	public void addAttachment(long recruitmentId,String filepath);
	
	/**
	 * 列出某一用户发布的招聘信息
	 * @param employerId
	 * @return
	 */
	public List<Recruitment> listRecruitment(long employerId);
	
	/**
	 * 得到某一条招聘信息
	 * @param id
	 * @return
	 */
	public Recruitment getRecruitment(long id);
	
	/**
	 * 删除某一条招聘信息
	 * @param id
	 */
	public void deleteRecruitment(long id);
	
	/**
	 * 列出某一条招聘信息的所有附件
	 * @param recruitmentId
	 * @return
	 */
	public List<Attachment> listAttachment(long recruitmentId);
}
