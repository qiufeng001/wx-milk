package wx.util.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此类是为了在处理时间的时候对时间进行初始化，转变为自己想要的时间格式
 * 
 * 使用方法，在实体类的时间属性上面引用这个类即可，
 * 反序列化一个固定格式的 Date
 * 例如：@JsonDeserializer(using = JsonDateDeserializer.class)
 * 
 * 
 * @Component 项目初始化的时候将此类加入容器中初始化
 * 
 * 
 * 拓展知识：Gson 是对 json 和 java 对象进行转换的 java api
 * 
 * @author Kevin
 *	
 * try it,do it best!
 */
@Component
public class JsonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) 
			throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(jsonParser.getText());
		} catch (ParseException e) {
			return null;
		}
	}
}
