package wx.query;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 2810879201359067432L;
	
	/**
	 * 分页的大小，一页显示多少条
	 */
	private int pageSize = 10;
	
	/**
	 * 当前页码，也就是第几页，比如第2页，则 pageIndex 等于 2
	 */
	private int pageIndex = 1;
	
	/**
	 * 记录总数
	 */
	private int total;
	
	/**
	 * 页总数
	 */
	private int pageTotal;
	
	public Page() {
		
	}
	
	public Page(int pageSize,int pageIndex) {
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pageTotal = (int)Math.ceil(total / pageSize);
	}

	public int getPageTotal() {
		return pageTotal;
	}
	
	/**
	 * 待解释
	 * @return
	 */
	public boolean next() {
		Integer value = this.pageIndex + 1;
		if(value > pageTotal) {
			return false;
		}
		this.pageIndex = value;
		return true;
	}
	
	/**
	 * 每次查询的时候 limit 前面的一个数字，就是 limit 0,20 中的 0
	 * @return
	 */
	public long getStartRowNum() {
		return (this.pageIndex - 1) * pageSize;
	}
}
