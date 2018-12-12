package wx.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtil {

    /**
     * 获取当前类的Log
     */
    public static Log getLog() {
        StackTraceElement[] stackEle = new RuntimeException().getStackTrace();
        return LogFactory.getLog(stackEle[1].getClass());
    }
}
