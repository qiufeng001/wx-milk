package wx.core;

/**
 * 数据转换接口
 * @author kain
 *
 */
public interface IConverter {
	Object convert(String dataType, Object value, Object other);
}
