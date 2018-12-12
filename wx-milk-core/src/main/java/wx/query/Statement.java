package wx.query;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import wx.util.Helper;
import wx.util.date.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Statement extends NodeStatement implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -256686039340849719L;
	private String name;
	private Object value;
	private String nodeType = "=";

	public Statement() {

	}

	public Statement(String name, String nodeType, Object value) {
		this(name,value);
		this.nodeType = nodeType;
	}

	public Statement(String name, String nodeType, Object value,int type) {
		this(name,nodeType,value);
		this.type = type;
	}

	public Statement(String name, Object value) {
		this.name = name;
		if (value instanceof String && value != null && !"ids".equalsIgnoreCase(name)) {
			this.value = StringEscapeUtils.escapeSql(value.toString());
		}

		else if(value instanceof Date && value != null){
			this.value=DateUtil.getDateTime((Date)value);
		}else{
			this.value = value;
		}


	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * =,>,<,like ...
	 *
	 * @return
	 */
	@Override
	public String getNodeType() {
		return nodeType;
	}
	@Override
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public String toString() {
		String f = Helper.camel2DbFormat(this.name);

		if (this.nodeType.equals("between")) {
			Object[] vals = (Object[]) this.value;
			return String.format("%s between '%s' and '%s' ", f, vals[0], vals[1]);
		} else if (this.nodeType.equals("Not In") || this.nodeType.equals("In")) {
			List<?> vals = (List<?>) this.value;
			String sql;
			if (vals != null && vals.size() > 0) {
				Object v = vals.get(0);
				String[] ary = new String[vals.size()];
				if (v instanceof String) {
					for (int i = 0; i < vals.size(); i++) {
						ary[i] = "'" + vals.get(i).toString() + "'";
					}
				} else if (v instanceof Date) {
					for (int i = 0; i < vals.size(); i++) {
						ary[i] = "'" + DateUtil.format1((Date) vals.get(i)) + "'";
					}
				} else {
					for (int i = 0; i < vals.size(); i++) {
						ary[i] = vals.get(i).toString();
					}
				}
				sql = StringUtils.join(ary, ",");
			} else {
				throw new NullPointerException("value 值不许为空");
			}
			return String.format("%s %s (%s)", f, nodeType, sql);
		}
		String format = "%s %s %s";
		if (this.value == null) {
			format = "%s %s null";
		} else if (this.value instanceof String || this.value instanceof Character || this.value instanceof Date) {
			format = "%s %s '%s'";
		}

		return String.format(format, f, this.nodeType, this.value);
	}
	private int type = IStatement.NORMAL;

	@Override
	public int getType() {
		return type;
	}

}
