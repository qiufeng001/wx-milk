package wx.base.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import wx.exception.InvalidOperationException;
import wx.util.date.DateUtil;

public class ExcelImport implements AutoCloseable {

	private XSSFWorkbook wb;
	private Sheet sheet;
	private int sheetIndex = 0;
	private boolean ignoreBlankRow = false;
	private List<ExcelColumn> columns;
	private List<ExcelColumn> header;

	public ExcelImport(List<ExcelColumn> columns) {
		this.columns = columns;
	}

	public void open(String file) throws IOException {
		File f = new File(file);
		FileInputStream stream = new FileInputStream(f.getAbsolutePath());
		open(stream, null);
	}

	public void open(InputStream stream) throws IOException {
		open(stream, null);
	}

	public void open(InputStream stream, String sheetName) throws IOException {
		OPCPackage pkg;
		try {
			pkg = OPCPackage.open(stream);
		} catch (InvalidFormatException e) {
			throw new IOException(e.getMessage(), e);
		}
		wb = new XSSFWorkbook(pkg);
		if (StringUtils.isEmpty(sheetName)) {
			sheet = wb.getSheetAt(sheetIndex);
		} else {
			sheetIndex = wb.getSheetIndex(sheetName);
			sheet = wb.getSheet(sheetName);
		}
		sheetRowCount = Math.min(sheet.getLastRowNum(), 100000); // 不准确
	}

	protected int getNumberOfSheets() {
		validate();
		return wb.getNumberOfSheets();
	}

	private Integer sheetRowIndex = 0;
	private Integer sheetRowCount = 100000;
	private Row row;
	private Integer headerIndex = 0;
	private Object[] rowData;

	public void readHeader() {
		sheetRowIndex = headerIndex;

		if (!read())
			throw new InvalidOperationException("没有合适的表头信息");

		List<ExcelColumn> ary = new ArrayList<>(rowData.length);
		for (int i = 0; i < rowData.length; i++) {
			String val = (String) rowData[i];
			if (StringUtils.isBlank(val)) {
				ary.add(null);
				continue;
			}
			ExcelColumn item = null;
			for (ExcelColumn col : columns) {
				if (val.equalsIgnoreCase(col.getTitle())) {
					item = col;
				}
			}
			if (item == null) {
				item = new ExcelColumn();
				item.setTitle(val);
				item.setField(val);
			}
			ary.add(item);
		}
		header = ary;
	}

	private String getCellImportType(int i) {
		if (i >= columns.size())
			return null;
		if (i >= header.size())
			return null;
		ExcelColumn col = header.get(i);
		return col.getDataType();
		// return columns.get(i).getDataType();
	}

	public int getRowCount() {
		validate();
		return sheetRowCount;
	}

	public boolean read() {
		validate();
		row = null;
		if (sheetRowIndex > sheetRowCount)
			return false;
		row = sheet.getRow(sheetRowIndex);
		if (row == null)
			return false;
		int count = Math.min(row.getLastCellNum(), 250);
		if (count <= 0)
			return false;
		boolean hasValue = false;
		rowData = new Object[count];
		for (int i = 0; i < count; i++) {
			String dataType = sheetRowIndex <= headerIndex ? "String" : getCellImportType(i);
			rowData[i] = getValue(row.getCell(i), dataType);
			if (!hasValue)
				hasValue = (rowData[i] != null);
		}
		sheetRowIndex += 1;
		return hasValue || ignoreBlankRow;
	}

	@SuppressWarnings("deprecation")
	protected Object getValue(Cell cell, String dataType) {
		if (cell == null)
			return null;

		CellType type = cell.getCellTypeEnum();
		if (type == CellType.STRING) {
			if ("date".equalsIgnoreCase(dataType))
				return DateUtil.parseToDate(cell.getStringCellValue(), "yyyy-MM-dd");
			else if ("datetime".equalsIgnoreCase(dataType))
				return DateUtil.parseToDate(cell.getStringCellValue(), "yyyy-MM-dd hh:mm:ss");
			String val = cell.getStringCellValue();
			if (val != null)
				return val.trim();
			
			return val;
		} else if (type == CellType.BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (type == CellType.NUMERIC) {
			if ("date".equalsIgnoreCase(dataType) || "datetime".equalsIgnoreCase(dataType))
				return cell.getDateCellValue();
			else if ("BigDecimal".equals(dataType))
				return new BigDecimal(cell.getNumericCellValue());
			else if ("int".equalsIgnoreCase(dataType) || "Integer".equalsIgnoreCase(dataType)) {
				if (HSSFDateUtil.isCellDateFormatted(cell))
					return DateUtil.format(cell.getDateCellValue(), "yyyy-MM-dd");
				return (int) cell.getNumericCellValue();
			}

			else if ("short".equalsIgnoreCase(dataType)) {
				if (HSSFDateUtil.isCellDateFormatted(cell))
					return DateUtil.format(cell.getDateCellValue(), "yyyy-MM-dd");
				return (short) cell.getNumericCellValue();
			} else if ("long".equalsIgnoreCase(dataType))
				return (long) cell.getNumericCellValue();
			else if ("String".equalsIgnoreCase(dataType)) {
				if (HSSFDateUtil.isCellDateFormatted(cell)){
					 return DateUtil.format(cell.getDateCellValue(), "yyyy-MM-dd");
					}else{
				//解决电话号码导入是数字的问题，by kzhang ,暂时不知道会不会引起其他问题		
				//	return cell.getNumericCellValue();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					return cell.getStringCellValue();
					}
			} else
				return cell.getNumericCellValue();
		} else if (type == CellType.BLANK)
			return null;
		else if (type == CellType.FORMULA)
			throw new InvalidOperationException();

		return null;
	}

	// protected Object getValue(Object val, String dataType) {
	// if (StringUtils.isBlank(dataType))
	// return (String) val;
	// if ("date".equalsIgnoreCase(dataType)) {
	// return DateUtil.parseToDate((String) val, "yyyy-MM-dd");
	// } else if ("datetime".equalsIgnoreCase(dataType)) {
	// return DateUtil.parseToDate((String) val, "yyyy-MM-dd hh:mm:ss");
	// } else if ("number".equalsIgnoreCase(dataType) ||
	// "int".equalsIgnoreCase(dataType)
	// || "Integer".equalsIgnoreCase(dataType)) {
	// return Integer.parseInt((String) val);
	// } else if ("BigDecimal".equals(dataType)) {
	// return new BigDecimal((String) dataType);
	// } else if ("boolean".equalsIgnoreCase(dataType)) {
	// return Boolean.parseBoolean((String) val);
	// } else if ("short".equalsIgnoreCase(dataType)) {
	// return Short.parseShort((String) val);
	// } else if ("long".equalsIgnoreCase(dataType)) {
	// return Long.parseLong((String) val);
	// } else {
	// return (String) val;
	// }
	//
	// }

	public Map<String, Object> asMap() {
		validate();
		if (rowData == null)
			return null;
		Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < rowData.length && i < header.size(); i++) {
			Object data = rowData[i];
			ExcelColumn column = header.get(i);
			if (column == null)
				continue;
			map.put(column.getField(), data);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public <T> T asObject(Class<?> clazz) {
		validate();
		if (rowData == null)
			return null;
		BeanWrapper bean = new BeanWrapperImpl(clazz);

		for (int i = 0; i < rowData.length; i++) {
			Object data = rowData[i];
			ExcelColumn column = header.get(i);
			if (column == null)
				continue;
			bean.setPropertyValue(column.getField(), data);
		}
		return (T) bean.getWrappedInstance();
	}

	public List<Object> asObjects() {
		validate();
		if (rowData == null)
			return null;
		Map<Class<?>, BeanWrapper> beans = new HashMap<>();
		// for (Class<?> cls : clazz) {
		// maps.put(cls, new BeanWrapperImpl(cls));
		// }
		Map<String, Object> values = new HashMap<String, Object>();
		for (int i = 0; i < rowData.length; i++) {
			Object data = rowData[i];
			ExcelColumn column = header.get(i);
			if (column == null)
				continue;
			BeanWrapper bean = null;
			if (column.getBeanClass() != null) {
				bean = beans.get(column.getBeanClass());
				if (bean == null) {
					bean = new BeanWrapperImpl(column.getBeanClass());
					beans.put(column.getBeanClass(), bean);
				}
			}
			if (bean != null)
				bean.setPropertyValue(column.getField(), data);
			else
				values.put(column.getField(), data);
		}
		List<Object> objects = new ArrayList<>(beans.size() + 1);
		for (BeanWrapper wrapper : beans.values()) {
			objects.add(wrapper.getWrappedInstance());
		}
		if (values.size() > 0)
			objects.add(values);
		return objects;
	}

	public boolean next() {
		validate();
		sheetIndex += 1;
		sheetRowIndex = 0;
		row = null;
		header = null;
		rowData = null;

		if (sheetIndex > wb.getNumberOfSheets())
			return false;

		if (sheet != null)
			wb.cloneSheet(sheetIndex - 1);

		sheet = wb.getSheetAt(sheetIndex);
		sheetRowCount = sheet.getLastRowNum();
		return true;
	}

	private void validate() {
		if (wb == null)
			throw new InvalidOperationException("请先打开一个可用的excel文件。");

	}

	@Override
	public void close() throws Exception {
		if (wb != null)
			wb.close();
		wb = null;
		sheet = null;
	}

}
