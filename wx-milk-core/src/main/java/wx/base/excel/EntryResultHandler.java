package wx.base.excel;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import wx.annotation.MapField;
import wx.base.manager.IEntryResultHandler;
import wx.core.ConverterManager;
import wx.core.IConverter;
import wx.domain.IName;

public abstract class EntryResultHandler<T> implements IEntryResultHandler<T>, Closeable {
	protected Logger logger = LogManager.getLogger(EntryResultHandler.class);
	protected ExcelExport excel;
	protected boolean autoWrappe = true;
	protected Map<String, IDataFormater> fieldFormater = new HashMap<>();
	protected Map<String, MapField> converts = new HashMap<>();
	protected List<Field> fields = new ArrayList<>();

	protected Class<?> clz;

	public ExcelExport getExcel() {
		return excel;
	}

	public void setExcel(ExcelExport excel) {
		this.excel = excel;
	}

	public EntryResultHandler(boolean autoWrappe, Class<?> clz) {
		this.autoWrappe = autoWrappe;
		this.clz = clz;
		this.init();
	}

	@Override
	public boolean handleResult(T entry) {
		excel.write(transform(entry));
		return true;
	}

	public void close() {
		excel = null;
	}

	private void init() {
		ReflectionUtils.doWithFields(clz, new FieldCallback() {

			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				if (Modifier.isStatic(field.getModifiers()))
					return;
				String name = field.getName();
				ExportIgnore ignored = field.getAnnotation(ExportIgnore.class);
				if (ignored != null && ignored.value()) {

					return;
				}
				fields.add(field);

				field.setAccessible(true);
				JsonSerialize js = field.getAnnotation(JsonSerialize.class);
				if (js != null) {
					try {
						@SuppressWarnings("rawtypes")
						JsonSerializer serializer = (JsonSerializer) js.using().newInstance();
						fieldFormater.put(name, new JsonDataFormater(serializer));
					} catch (Exception e) {
					}
				}

				MapField mapfield = field.getAnnotation(MapField.class);
				if (mapfield != null) {
					converts.put(name, mapfield);
					String formatter = mapfield.formatter();
					if (!StringUtils.isEmpty(formatter)) {
						IDataFormater df = DataFormaterManager.get(formatter);
						if (df != null)
							fieldFormater.put(name, df);
						else
							logger.warn("没有找到对应的formatter:" + formatter);
					}
				}

			}
		});

	}

	protected String defaultCacheGroup = "mdm";

	private String getBeanNameValue(Object bean) {
		if (bean == null)
			return null;
		if (bean instanceof IName)
			return ((IName) bean).getName();

		Class<?> clazz = bean.getClass();
		try {
			Field f = clazz.getDeclaredField("name");
			f.setAccessible(true);
			return (String) f.get(bean);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 处理字段值
	 * 
	 * @param data
	 *            对象转换成的Map，key为field，value为值
	 * @param field
	 *            grid column定义的dataFeild,如果dataField没有提供，则去column的field属性
	 * @param value
	 *            field对应的value
	 * @return 处理过的值
	 */
	protected Object handleValue(Map<String, Object> data, String field, Object value) {
			MapField mapfield = this.converts.get(field);
			if (mapfield == null)
				return value;
			String convertName = mapfield.converter();
			if (convertName == null)
				convertName = "default";
			convertName += "Converter";

			IConverter convert = ConverterManager.get(convertName);
			if (convert == null) {
				logger.warn("没有找到对应的转换器:" + convertName);
				return value;
			} else {
				return convert.convert(mapfield.dataType(), value, data);
			}

	}

	protected Map<String, Object> transform(T entry) {
		Map<String, Object> data = handleEntry(entry);
		ExcelColumn[] header = excel.getHeader();
		for (int i = 0; i < header.length; i++) {
			ExcelColumn col = header[i];
			String field = col.getDataField();
			if (StringUtils.isEmpty(field))
				continue;

			Object val = handleValue(data, field, data.get(field));

			IDataFormater formater = fieldFormater.get(field);
			if (formater != null) {
				val = formater.format(val);
			}

			data.put(field, val);
		}
		return data;
	}

	protected Map<String, Object> handleEntry(T entry) {
		HashMap<String, Object> map = new HashMap<>();
		for (Field field : fields) {
			try {
				Object value = field.get(entry);
				map.put(field.getName(), value);
			} catch (Exception e) {

			}
		}
		return map;
	}
}
