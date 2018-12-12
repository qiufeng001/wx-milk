package wx.query;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import wx.util.Helper;
import wx.util.JsonUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询条件定义 查询对象： id：不知道干啥的; name: 也不知道干啥的; conditions： 查询SQL,直接拼装成查询SQL;
 * sorts：SQL的排序字段; groups： SQL的汇总字段;
 *
 * @author kain
 *
 */
public class Query implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6617362131796003330L;
	String id;
	String name;

	private Map<String, Object> paramsMap;

	List<IStatement> conditions = new ArrayList<>();
	List<Sort> sorts = new ArrayList<>();
	List<Group> groups = new ArrayList<>();

	public static Query empty() {
		return new Query();
	}

	private Pagenation page;

	public Pagenation getPagenation() {
		return page;
	}

	public List<IStatement> getConditions() {
		return conditions;
	}

	public IStatement find(String name) {
		for (IStatement item : conditions) {
			IStatement st = find(item, name);
			if (st != null) {
				return st;
			}
		}
		return null;
	}

	private IStatement find(IStatement st, String name) {
		if (st.getType() == IStatement.NORMAL) {
			Statement item = (Statement) st;
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			} else {
				return null;
			}

		} else if (st.getType() == IStatement.NODE) {
			IStatement iv = find(st.getLeft(), name);
			if (iv != null) {
				return iv;
			}
			return find(st.getRight(), name);
		}
		return null;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public Query page(int pageSize, int pageIndex) {
		page = new Pagenation(pageIndex, pageSize);
		return this;
	}

	public static Query Where(String name, Object value) {
		Query q = new Query();
		q.conditions.add(new Statement(name, value));
		return q;
	}

	public static Query fromJson(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JsonNode node = JsonUtils.fromJson(json);
		Query q = new Query();
		if (node.has("name")) {
			q.name = node.get("node").asText();
		}
		if (node.has("conditions") && node.get("conditions").isArray()) {
			ArrayNode nodes = (ArrayNode) node.get("conditions");
			for (JsonNode sub : nodes) {
				Integer type = IStatement.NORMAL;
				if (sub.has("type")) {
					type = sub.get("type").asInt(IStatement.NORMAL);
				}
				IStatement st;
				if (type == IStatement.NORMAL) {
					st = JsonUtils.fromJson(sub.toString(), Statement.class);
				}else{
					st = JsonUtils.fromJson(sub.toString(), NodeStatement.class);
				}
				q.conditions.add(st);
			}
		}
		if (node.has("sorts") && node.get("sorts").isArray()) {
			q.sorts = JsonUtils.fromListJson(node.get("sorts").toString(), Sort.class);
		}
		return q;
	}

	public Query where(String name, Object value) {
		conditions.add(new Statement(name, value));
		return this;
	}

	public Query where(IStatement where) {
		conditions.add(where);
		return this;
	}

	public Query and(String name, Object value) {
		conditions.add(new Statement(name, value));
		return this;
	}

	public Query and(IStatement statement) {
		conditions.add(statement);
		return this;
	}

	public Query orderby(String name) {
		this.sorts.add(new Sort(name));
		return this;
	}

	public Query orderby(String name, boolean desc) {
		this.sorts.add(new Sort(name, desc));
		return this;
	}

	public Query groupBy(String name) {
		this.groups.add(new Group(name));
		return this;
	}

	public String getSort() {
		String val = "";
		for (int i = 0; i < sorts.size(); i++) {
			String f = Helper.camel2DbFormat(sorts.get(i).toString());
			if (i == 0) {
				val += f;
			}else{
				val += "," + f;
			}
		}
		return val;
	}

	/**
	 * 获取GroupBy的字段
	 *
	 * @return 汇总字段用逗号“,”分隔
	 */
	public String getGroupField() {
		String groupByField = "";
		for (int i = 0; i < groups.size(); i++) {
			String field = Helper.camel2DbFormat(groups.get(i).getGroupName());
			if (i == 0) {
				groupByField += field;
			} else {
				groupByField += "," + field;
			}
		}
		return groupByField;
	}

	/**
	 * 清空GroupBy字段列表
	 */
	public void clearGroup() {
		groups.clear();
	}

	final static String DYNAMIC_FIELD = "queryCondition";
	final static String GROUP_FIELD = "groupCondition";

	/**
	 * 将入参对象分解成键值对，作为查询条件
	 *
	 * @param vals
	 * @return
	 */
	public Query append(Object vals) {
		if (vals == null) {
			return this;
		}

		BeanWrapper bean = new BeanWrapperImpl(vals);
		for (PropertyDescriptor p : bean.getPropertyDescriptors()) {
			this.and(p.getName(), bean.getPropertyValue(p.getName()));
		}
		return this;
	}

	public Map<String, Object> asMap() {

		HashMap<String, Object> map = new HashMap<>();
		List<String> dynamic = new ArrayList<>();

		Map<String, Object> parmas = this.getParamsMap();
		if(parmas != null) {
			for (String key : parmas.keySet()) {
				Statement statement = new Statement();
				statement.setName(key);
				statement.setValue(parmas.get(key));
				conditions.add(statement);
			}
		}


		for (IStatement item : conditions) {
			if (item.getType() == IStatement.NORMAL) {
				Statement st = (Statement) item;
				map.put(st.getName(), st.getValue());
			} else if (item.getType() == IStatement.NODE) {
				dynamic.add(item.toString());
			}
		}
		if (dynamic.size() > 0) {
			String sql = "(" + StringUtils.join(dynamic, " AND ") + ")";
			Object val = map.get(DYNAMIC_FIELD);
			if (val != null) {
				sql += " AND (" + val.toString() + ")";
			}
			map.put(DYNAMIC_FIELD, sql);
		}
		if (groups.size() > 0) {
			map.put(GROUP_FIELD, getGroupField());
		}
		return map;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
}
