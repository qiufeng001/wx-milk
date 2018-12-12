package wx.base.excel;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import wx.annotation.MapField;

public class ExcelColumn implements Serializable {
	public ExcelColumn(){
		
	}
	public ExcelColumn(String title,String field){
		this.field = field;
		this.title = title;
		this.dataType = "String";
	}
	
	public ExcelColumn(String title,String field,String dataType){
		this.field = field;
		this.title = title;
		this.dataType = dataType;
	}
	

	@Override
	public String toString() {
		return "ExcelColumn [field=" + field + ", title=" + title + ", width=" + width + ", align=" + align
				+ ", orderNo=" + orderNo + ", dataType=" + dataType + ", colspan=" + colspan + ", rowspan=" + rowspan
				+ ", dataField=" + dataField + ", beanClass=" + beanClass + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2674327408767477777L;

	private String field;

	private String title;

	private Integer width = 0;

	private String align;

	private Integer orderNo = 0;

	private String dataType;

	private Integer colspan;

	private Integer rowspan;

	private String dataField;
	
	@JsonIgnore
	private Class<?> beanClass;
	
	@JsonIgnore
	private MapField mapField;	
	
	public MapField getMapField() {
		return mapField;
	}
	public void setMapField(MapField mapField) {
		this.mapField = mapField;
	}
	
	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public String getDataField() {
		if(StringUtils.isEmpty(dataField))
			return field;
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
