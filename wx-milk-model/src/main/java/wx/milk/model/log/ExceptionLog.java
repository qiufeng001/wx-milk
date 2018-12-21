package wx.milk.model.log;


import com.framework.core.security.BasicEntity;

/**
 * auther: kiven on 2018/8/31/031 9:33
 * try it bast!
 */
public class ExceptionLog extends BasicEntity {

    private String exceptionJson;
    private String exceptionMsg;

    public String getExceptionJson() {
        return exceptionJson;
    }

    public void setExceptionJson(String exceptionJson) {
        this.exceptionJson = exceptionJson;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
