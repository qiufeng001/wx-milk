package wx.base.excel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 封装导出符合表头属性的对象
 * 
 * @author 杨勇
 * @date 2014-7-8 上午10:05:26
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings("rawtypes")
public class ExportDefinition implements Serializable {

	private static final long serialVersionUID = -7906127683466026480L;
	
	/** 文件名 */
	private String fileName;
	
	/** 复合表头的第一个表头字段集合 */
	private List<Map> headerColumnsMapList;
	
	/**复合表头的第三行集合*/
	private List<Map> footerColumnsMapList;
	
	/** 主表的表头字段集合 */
	private List<Map> columnsMapList;
	
	/** 主表的数据集合 */
	private List<Map> dataMapList;
	
	/** 子表的表头字段集合 */
	private List<Map> subColumnsMapList;
	
	/** 导出excel过程中，如果需要访问导的第几行数据，则，需要给定这个参数为访问的excel行数；如传递的为空，则默认值为1行，
	 * 推荐使用默认值。 例如：如果想在程序中取得最后100行的数据，那么该参数=100； 否则就按照默认值导出。 */
	private Integer rowAccessWindowSize;

	public List<Map> getHeaderColumnsMapList() {
		return headerColumnsMapList;
	}

	public void setHeaderColumnsMapList(List<Map> headerColumnsMapList) {
		this.headerColumnsMapList = headerColumnsMapList;
	}

	public List<Map> getFooterColumnsMapList() {
		return footerColumnsMapList;
	}

	public void setFooterColumnsMapList(List<Map> footerColumnsMapList) {
		this.footerColumnsMapList = footerColumnsMapList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Map> getColumnsMapList() {
		return columnsMapList;
	}

	public void setColumnsMapList(List<Map> columnsMapList) {
		this.columnsMapList = columnsMapList;
	}

	public List<Map> getDataMapList() {
		return dataMapList;
	}

	public void setDataMapList(List<Map> dataMapList) {
		this.dataMapList = dataMapList;
	}

	public List<Map> getSubColumnsMapList() {
		return subColumnsMapList;
	}

	public void setSubColumnsMapList(List<Map> subColumnsMapList) {
		this.subColumnsMapList = subColumnsMapList;
	}

	public Integer getRowAccessWindowSize() {
		return rowAccessWindowSize;
	}

	public void setRowAccessWindowSize(Integer rowAccessWindowSize) {
		this.rowAccessWindowSize = rowAccessWindowSize;
	}
}
