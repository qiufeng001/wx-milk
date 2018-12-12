package wx.base.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import wx.annotation.Label;
import wx.annotation.MapField;

public class ExcelUtils {

	/**
	 * 
	 * @param cls
	 * @return
	 */
	public static List<ExcelColumn> getBeanInfo(Class<?> cls) {
		List<ExcelColumn> ary = new ArrayList<>(); 
		while (true) {
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				Label label = field.getAnnotation(Label.class);
				if (label == null)
					continue;
				MapField mapField = field.getAnnotation(MapField.class);
				ExcelColumn col = new ExcelColumn();
				col.setTitle(label.value());
				col.setField(name);
				col.setDataType(field.getType().getSimpleName());
				col.setBeanClass(cls);
				col.setMapField(mapField);
				ary.add(col);
			}
			cls = cls.getSuperclass();
			if (Object.class.equals(cls))
				break;
		}

		return ary;
	}
	
	public static List<ExcelColumn> getBeanInfo(Class<?> ...cls) {
		List<ExcelColumn> ary = new ArrayList<>();
		for (Class<?> c : cls) {
			ary.addAll(getBeanInfo(c));
		}
		return ary;
	}

}
