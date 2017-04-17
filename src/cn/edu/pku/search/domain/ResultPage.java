package cn.edu.pku.search.domain;

import java.util.List;

/**
 * 分页类
 * @author lanzheng
 *
 * @param <T> 数据类型
 */
public class ResultPage<T> {

	private List<T> datas;
	private int offset; //偏移量
	private int size; //每页数据大小
	private long total; //总数量
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	
}
