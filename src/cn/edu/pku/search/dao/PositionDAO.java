package cn.edu.pku.search.dao;

import java.util.List;

import cn.edu.pku.search.domain.Attachment;
import cn.edu.pku.search.domain.PositionJobpopo;
import cn.edu.pku.search.domain.Position;

public interface PositionDAO {

	/**
	 * 添加招聘信息
	 * @param position
	 */
	public void addPosition(PositionJobpopo position);
	
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
	public List<PositionJobpopo> listPosition(long employerId);
	
	/**
	 * 得到某条招聘信息
	 * @param id
	 * @return
	 */
	public PositionJobpopo loadPosition(long id);
	
	/**
	 * 得到某条从外部爬取的招聘信息
	 * @param id
	 * @return
	 */
	public Position loadPositionBbs(long id);
	
	/**
	 * 删除招聘信息
	 * @param id
	 */
	public void deletePosition(long id);
	
	/**
	 * 删除附件
	 * @param attachment
	 */
	public void deleteAttachment(Attachment attachment);
	
	/**
	 * 得到某条招聘信息的附件
	 * @param positionId
	 * @return
	 */
	public List<Attachment> listAttachment(long positionId);
	
	/**
	 * 列出所有的招聘信息
	 * @return
	 */
	public List<PositionJobpopo> listAllPosition();
	
	/**
	 * 列出某一部分招聘信息
	 * @param offset 偏移量
	 * @param size 返回的结果条数
	 * @return
	 */
	public List<Position> listPositionBBS(int offset,int size);
	
	/**
	 * 列出某一部分招聘信息
	 * @param offset 偏移量
	 * @param size 返回的结果条数
	 * @return
	 */
	public List<PositionJobpopo> listPosition(int offset, int size);
	
	/**
	 * 更新BBS招聘信息
	 * @param position 招聘信息
	 * @return
	 */
	public void updateBBS(Position positionBBS);
	
	/**
	 * 更新招聘信息
	 * @param position 招聘信息
	 * @return
	 */
	public void update(PositionJobpopo position);
}
