package wx.util.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 此类是为了在处理时间的时候对时间进行初始化，转变为自己想要的时间格式
 * 
 * 使用方法，在实体类的时间属性上面引用这个类即可， 反序列化一个固定格式的 Date 例如：@JsonSerializer(using =
 * JsonDateSerializer.class)
 * 
 * 
 * @Component 项目初始化的时候将此类加入容器中初始化
 * 
 * 
 *            拓展知识：Gson 是对 json 和 java 对象进行转换的 java api
 * 
 * @author Kevin
 * 
 *         try it,do it best!
 */
@Component
public class JsonDefaultDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		
		// 获得默认的日期格式，即转换后的数据（YY-mm-dd HH:mm:ss）
		String dateStr = DateSerializerUtils.formatDefalt(date);
		jsonGenerator.writeString(dateStr);
	}
}