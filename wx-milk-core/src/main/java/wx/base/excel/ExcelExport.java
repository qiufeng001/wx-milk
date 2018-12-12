package wx.base.excel;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import wx.util.NumberValidationUtils;

@SuppressWarnings("deprecation")
public class ExcelExport implements AutoCloseable, IExcelWorker {

	protected Log logger = LogFactory.getLog(getClass());

	private SXSSFWorkbook wb;
	private Sheet sheet;
	// private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss");
	// private CellStyle headStyle;
	private ExcelColumn[][] columns;

	public ExcelColumn[][] getColumns() {
		return columns;
	}

	private ExcelColumn[] header;

	public ExcelColumn[] getHeader() {
		return header;
	}

	private CellStyle contentStyle;
	private CellStyle floatContentStyle;
	private CellStyle numberContentStyle;
	private Integer rowIndex = -1;
	private Map<String, IDataFormater> fieldFormater;

	public ExcelExport(ExcelColumn[][] columns) {
		this.columns = columns;
		header = columns[columns.length - 1];
		fieldFormater = new HashMap<String, IDataFormater>();
	}

	public void open(String fileName) {
		this.open(fileName, 10);
	}

	public void open(String fileName, int rowAccessWindowSize) {

		wb = new SXSSFWorkbook(rowAccessWindowSize);
		sheet = wb.createSheet();
		((SXSSFSheet) sheet).setRandomAccessWindowSize(10);
		wb.setSheetName(0, fileName);
		sheet.setDefaultColumnWidth((short) 18);

		// headStyle = getHeaderStyle(columns.length > 1);

		contentStyle = getContentStyle(false);
		numberContentStyle = getContentStyle(true);
		floatContentStyle = getContentFloatStyle();

		buildHeader();
	}

	private int nextRow() {
		rowIndex += 1;
		return rowIndex;
	}

	private void buildHeader() {
		CellStyle style = getFixHeaderStyle();
		for (ExcelColumn[] ExcelColumns : columns) {
			nextRow();
			Row row = sheet.createRow(rowIndex);
			Integer index = 0;
			for (ExcelColumn col : ExcelColumns) {
				Cell headerCell = row.createCell(index);
				index += 1;
				headerCell.setCellType(HSSFCell.ENCODING_UTF_16);
				headerCell.setCellValue(col.getTitle());
				headerCell.setCellStyle(style);
			}
		}
	}

	private boolean initedDataFormatInfo = false;

	@SuppressWarnings("unchecked")
	@Override
	public void write(Object data) {
		if (data instanceof Map)
			write(((Map<String, Object>) data));
		BeanWrapper wrapper = new BeanWrapperImpl(data);
		PropertyDescriptor[] ps = wrapper.getPropertyDescriptors();
		Map<String, Object> map = new HashMap<>();
		Class<?> dataClass = data.getClass();
		for (PropertyDescriptor p : ps) {
			String name = p.getName();
			Object val = wrapper.getPropertyValue(p.getName());
			map.put(name, val);
			if (!initedDataFormatInfo) {
				try {
					Field field = ReflectionUtils.findField(dataClass, name);
					JsonSerialize js = field.getAnnotation(JsonSerialize.class);
					if (js != null) {
						try {
							@SuppressWarnings("rawtypes")
							JsonSerializer serializer = (JsonSerializer) js.using().newInstance();
							fieldFormater.put(name, new JsonDataFormater(serializer));
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}
			}

			IDataFormater formater = fieldFormater.get(name);
			if (formater != null) {
				Object obj = formater.format(val);
				map.put(name, obj);
			}

		}
		initedDataFormatInfo = true;
		write(map);
	}

	public void write(Map<String, Object> data) {
		nextRow();
		Row row = sheet.createRow(rowIndex); // 第三行开始填充数据
		for (int i = 0; i < header.length; i++) {
			Cell cell = row.createCell(i);
			Object cellValue = StringUtils.EMPTY;
			ExcelColumn col = header[i];
			String field = col.getField();
			if (field != null) {
				cellValue = data.get(field);
			}
			if (cellValue == null) {
				cell.setCellValue(StringUtils.EMPTY);
			} else {

				// 设置单元格样式
				String dataType = col.getDataType();
				if (dataType != null && "number".equalsIgnoreCase(dataType)) {
					cell.setCellStyle(numberContentStyle);
					cell.setCellValue(Double.parseDouble(cellValue.toString()));
				} else if ("String".equalsIgnoreCase(dataType)) {
					cell.setCellStyle(contentStyle);
					cell.setCellValue(cellValue.toString());
				} else {
					if (NumberValidationUtils.isRealNumber(cellValue.toString())) {
						if (NumberValidationUtils.isInteger(cellValue.toString())) {
							cell.setCellStyle(numberContentStyle);
							cell.setCellValue(Integer.parseInt(cellValue.toString()));
						} else {
							cell.setCellStyle(floatContentStyle);
							cell.setCellValue(Double.parseDouble(cellValue.toString()));
						}
					} else {
						cell.setCellStyle(contentStyle);
						cell.setCellValue(cellValue.toString());
					}
				}
			}
		}
	}

	@Override
	public Integer getRowIndex() {
		return rowIndex;
	}

	@Override
	public void flush(OutputStream stream) throws IOException {
		for (int i = 0; i < header.length; i++) {
			Integer width = header[i].getWidth();
			if (null != width && width > 0) {
				int w = width * 256 / 7;
				sheet.setColumnWidth(i, w);

			}
		}
		wb.write(stream);
		stream.flush();
	}

	@Override
	public void close() throws Exception {
		sheet = null;
		contentStyle = null;
		numberContentStyle = null;
		if (wb != null)
			wb.dispose();
		wb = null;
	}

	private CellStyle getContentStyle(Boolean isNumber) {
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 10);
		bodyFont.setFontName("宋体");
		CellStyle bodyStyle = wb.createCellStyle();
		if (isNumber) {
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		} else {
			DataFormat format = wb.createDataFormat();
			bodyStyle.setDataFormat(format.getFormat("@"));
		}
		// bodyStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		// bodyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(false);
		return bodyStyle;
	}

	private CellStyle getContentFloatStyle() {
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 10);
		bodyFont.setFontName("宋体");
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		bodyStyle.setFont(bodyFont);
		bodyStyle.setWrapText(false);
		return bodyStyle;
	}

	CellStyle getFixHeaderStyle() {
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName("宋体");
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		// headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		return headerStyle;
	}
}
