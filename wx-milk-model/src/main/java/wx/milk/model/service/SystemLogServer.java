package wx.milk.model.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.framework.core.security.BasicEntity;
import com.framework.core.util.date.JsonDateDeserializer;
import com.framework.core.util.date.JsonDefaultDateSerializer;
import java.util.Date;

/**
 * auther: kiven on 2018/8/24/024 18:07
 * try it bast!
 */
public class SystemLogServer extends BasicEntity {
    /* 操作人id */
    private String userId;
    /* 操作的ip */
    private String ip;
    /* 执行的方法 */
    private String exctionMethod;

    /* 执行时间 */
    @JsonSerialize(using = JsonDefaultDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date executeDate;

    /*  */
    private String type;
    private String exceptionCode;
    private String description;
    private String params;
    private String exceptionDetail;
    /*  */

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getExctionMethod() {
        return exctionMethod;
    }

    public void setExctionMethod(String exctionMethod) {
        this.exctionMethod = exctionMethod;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }
}
