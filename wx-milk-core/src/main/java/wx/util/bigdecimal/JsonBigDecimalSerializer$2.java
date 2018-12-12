package wx.util.bigdecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 类名 JsonBigDecimalSerializer$2 后面的数字代表要保留的小数位
 * @author Kevin
 *	
 * try it,do it best!
 */
@Component
public class JsonBigDecimalSerializer$2 extends JsonSerializer<BigDecimal>{

	/**
	 * 将 BigDecimal 数据转换为 两位小数的数据格式
	 */
	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {			
		String bigDecimalStr = BigDecimalUtils.formatToStr(value, "0.00");
		gen.writeString(bigDecimalStr);
	}
}
