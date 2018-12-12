package wx.util;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import wx.exception.InvalidOperationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JSON的工具类
 * 
 * @author dong.j
 * @date 2013-10-31 下午2:31:06
 * @version 0.1.0
 * @copyright yougou.com
 */
@SuppressWarnings("deprecation")
public final class JsonUtils {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = generateMapper(Include.NON_NULL);
	}

	private JsonUtils() {
	}

	/**
	 * 将json通过类型转换成对象
	 * 
	 * <pre>
	 *     {@link JsonUtils JsonUtils}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return clazz.equals(String.class) ? (T) json : objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new InvalidOperationException(e);
		}
	}

	public static JsonNode fromJson(String json) {
		JsonNode node;
		try {
			node = objectMapper.readTree(json);
			return node;
		} catch (IOException e) {
			throw new InvalidOperationException(e);
		}

	}

	/**
	 * 将json通过类型转换成对象
	 * 
	 * <pre>
	 *     {@link JsonUtils JsonUtils}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
	 * </pre>
	 * 
	 * @param json
	 *            json字符串
	 * @param typeReference
	 *            引用类型
	 * @return 返回对象
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, TypeReference<?> typeReference) {
		try {
			return (T) (typeReference.getType().equals(String.class) ? json
					: objectMapper.readValue(json, typeReference));
		} catch (IOException e) {
			throw new InvalidOperationException(e);
		}
	}

	/**
	 * 将json通过类型转换成List集合
	 * 
	 * <pre>
	 *     {@link JsonUtils JsonUtils}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
	 * </pre>
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> List<T> fromListJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json,
					objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (IOException e) {
			throw new InvalidOperationException(e);
		}

	}

	/**
	 * 根据json串和节点名返回节点
	 * 
	 * @param json
	 *            json字符串
	 * @param nodeName
	 *            json节点名称
	 * @return
	 */
	public static JsonNode getNode(String json, String nodeName) {
		JsonNode node = null;
		try {
			node = objectMapper.readTree(json);
			return node.get(nodeName);
		} catch (Exception e) {
			throw new InvalidOperationException(e);
		}
	}

	/**
	 * 将对象转换成json
	 * 
	 * <pre>
	 *     {@link JsonUtils JsonUtils}.toJson(user);
	 * </pre>
	 * 
	 * @param src
	 *            对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src) {
		try {
			return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			throw new InvalidOperationException(e);
		}
	}

	/**
	 * 将对象转换成json, 可以设置输出属性
	 * 
	 * <pre>
	 *     {@link JsonUtils JsonUtils}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
	 * </pre>
	 * 
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 * 
	 * @param src
	 *            对象
	 * @param inclusion
	 *            传入一个枚举值, 设置输出属性
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, Include inclusion) {
		if (src instanceof String) {
			return (String) src;
		} else {
			ObjectMapper customMapper = generateMapper(inclusion);
			try {
				return customMapper.writeValueAsString(src);
			} catch (JsonProcessingException e) {
				throw new InvalidOperationException(e);
			}
		}
	}

	/**
	 * 将对象转换成json, 传入配置对象
	 * 
	 * <pre>
	 *     {@link ObjectMapper ObjectMapper}mapper = new ObjectMapper();
	 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
	 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
	 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
	 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
	 *     {@link JsonUtils JsonUtils}.toJson(user, mapper);
	 * </pre>
	 * 
	 * {@link ObjectMapper ObjectMapper}
	 * 
	 * @see ObjectMapper
	 * 
	 * @param src
	 *            对象
	 * @param mapper
	 *            配置对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, ObjectMapper mapper) {
		if (null != mapper) {
			if (src instanceof String) {
				return (String) src;
			} else {
				try {
					return mapper.writeValueAsString(src);
				} catch (JsonProcessingException e) {
					throw new InvalidOperationException(e);
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
	 * 
	 * @return {@link ObjectMapper ObjectMapper}对象
	 */
	public static ObjectMapper mapper() {
		return objectMapper;
	}

	/**
	 * 通过Inclusion创建ObjectMapper对象
	 * 
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 * 
	 * @param inclusion
	 *            传入一个枚举值, 设置输出属性
	 * @return 返回ObjectMapper对象
	 */
	private static ObjectMapper generateMapper(Include inclusion) {
		ObjectMapper mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		mapper.setSerializationInclusion(inclusion);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true); 
		
		// 所有日期格式都统一为以下样式
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper;
	}
}