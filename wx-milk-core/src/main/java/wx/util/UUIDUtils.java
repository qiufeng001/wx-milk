package wx.util;

import java.util.UUID;

/**
 * Created by Administrator on 2018/6/13/013.
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
