package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.Recruitment;
import cn.edu.pku.search.domain.RecruitmentBBS;

public interface RecruitmentDAO {

	/**
	 * 添加招聘信息
	 * @param recruitment
	 */
	public void addRecruitment(Recruitment recruitment);
	
	/**
	 * 添加附件
	 * @param attachment
	 */
	public void addAttachment(Attachment attachment);
	
	/**
	 * 列出某用户发布的招聘信息
	 * @param employerId
	 * @return
	 */
	public List<Recruitment> listRecruitment(long employerId);
	
	/**
	 * 得到某条招聘信息
	 * @param id
	 * @return
	 */
	public Recruitment loadRecruitment(long id);
	
	/**
	 * 得到某条从外部爬取的招聘信息
	 * @param id
	 * @return
	 */
	public RecruitmentBBS loadRecruitmentBbs(long id);
	
	/**
	 * 删除招聘信息
	 * @param id
	 */
	public void deleteRecruitment(long id);
	
	/**
	 * 删除附件
	 * @param attachment
	 */
	public void deleteAttachment(Attachment attachment);
	
	/**
	 * 得到某条招聘信息的附件
	 * @param recruitmentId
	 * @return
	 */
	public List<Attachment> listAttachment(long recruitmentId);
	
	/**
	 * 列出所有的招聘信息
	 * @return
	 */
	public List<Recruitment> listAllRecruitment();
	
	/**
	 * 列出某一部分招聘信息
	 * @param offset 偏移量
	 * @param size 返回的结果条数
	 * @return
	 */
	public List<RecruitmentBBS> listRecruitmentBBS(int offset,int size);
	
	/**
	 * 列出某一部分招聘信息
	 * @param offset 偏移量
	 * @param size 返回的结果条数
	 * @return
	 */
	public List<Recruitment> listRecruitment(int offset, int size);
	
}
