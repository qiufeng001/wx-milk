package wx.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import wx.util.JsonUtils;


/**
 * 查询语句构造
 * 
 * @author kain
 *
 */
public final class Q {

	public static Query empty() {
		return new Query();
	}

	public static Query q(String name, Object value) {
		Query q = new Query();
		q.conditions.add(new Statement(name, value));
		return q;
	}

	public static Query where(IStatement statement) {
		Query q = new Query();
		q.conditions.add(statement);
		return q;
	}

	public static Query where(String name, Object value) {
		return where(new Statement(name, value));
	}

	public static IStatement Equals(String name, Object value) {
		return new Statement(name, "=", value);
	}

	public static IStatement NotEquals(String name, Object value) {
		return new Statement(name, "!=", value);
	}

	public static IStatement Contains(String name, String value) {
		return new Statement(name, "like", "%" + value + "%");
	}

	public static IStatement StartWith(String name, String value) {
		return new Statement(name, "like", value + "%");
	}

	public static IStatement EndWith(String name, String value) {
		return new Statement(name, "like", "%" + value);
	}

	public static IStatement LessThen(String name, Object value) {
		return new Statement(name, "<", value);
	}

	public static IStatement GreatThen(String name, Object value) {
		return new Statement(name, ">", value);
	}

	public static IStatement LessThenEquals(String name, Object value) {
		return new Statement(name, "<=", value);
	}

	public static IStatement GreatThenEquals(String name, Object value) {
		return new Statement(name, ">=", value);
	}

	public static IStatement Between(String name, Object val1, Object val2) {
		return new Statement(name, "between", new Object[] { val1, val2 });
	}

	public static IStatement In(String name, String... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "In", lst, IStatement.NODE);
	}

	public static IStatement In(String name, int... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "In", lst, IStatement.NODE);
	}

	public static IStatement In(String name, Date... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "In",lst, IStatement.NODE);
	}

	public static IStatement NotIn(String name, int... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "Not In", lst, IStatement.NODE);
	}

	public static IStatement NotIn(String name, String... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "Not In", lst, IStatement.NODE);
	}

	public static IStatement NotIn(String name, Date... vals) {
		List<Object> lst = new LinkedList<>();
		for (Object object : vals) {
			lst.add(object);
		}
		return new Statement(name, "Not In",lst, IStatement.NODE);
	}

	public static IStatement And(IStatement a, IStatement b) {
		NodeStatement st = new NodeStatement(a, "AND", b);
		return st;
	}

	public static IStatement Or(IStatement a, IStatement b) {
		NodeStatement st = new NodeStatement(a, "OR", b);
		return st;
	}

	public static List<IStatement> getStatements(String json) {
		JsonNode node = JsonUtils.fromJson(json);
		List<IStatement> sts = new ArrayList<>();
		if (node.isArray()) {
			ArrayNode nodes = (ArrayNode) node;// JsonUtils.fromJson(json);
			for (JsonNode n : nodes) {
				sts.add(getStatement(n.toString()));
			}
		} else {
			sts.add(getStatement(json));
		}
		return sts;
	}

	public static IStatement getStatement(String json) {
		if (StringUtils.isEmpty(json) || "null".equals(json))
			return null;
		JsonNode node = JsonUtils.fromJson(json);
		String nodeType = node.get("nodeType").asText();
		IStatement st = null;
		IStatement left = null;
		if (node.get("left") != null)
			left = getStatement(node.get("left").toString());

		IStatement right = null;
		if (node.get("right") != null)
			right = getStatement(node.get("right").toString());
		if ("AND".equals(nodeType) || "OR".equals(nodeType)) {
			st = new NodeStatement(left, nodeType, right);
		} else {
			String name = node.get("name").asText();
			st = new Statement(name, node.get("value").asText());// 暂时全部处理成字符类型
			st.setNodeType(nodeType);
			st.setLeft(left);
			st.setRight(right);
		}
		return st;
	}

	// public static DynamicStatement and(Statement s){
	// return new Statement(name, value);
	// }
	//
	// public static Statement or( Statement s ){
	// return new Statement(name,"or","=", value);
	// }

}
