//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package wx.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JsonUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JsonUtil() {
    }

    public static String obj2Json(Object obj, boolean ignoreNull) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if(ignoreNull) {
                mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
            }

            mapper.getSerializationConfig().setDateFormat(sdf);
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException var4) {
            var4.printStackTrace();
        } catch (JsonMappingException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return "";
    }

    public static <X> X json2Obj(String jsonStr, Class<X> x) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonStr, x);
        } catch (JsonParseException var4) {
            var4.printStackTrace();
        } catch (JsonMappingException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return null;
    }
}
