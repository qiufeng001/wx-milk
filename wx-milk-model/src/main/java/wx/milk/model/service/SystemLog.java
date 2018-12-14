package wx.milk.model.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import wx.security.BasicEntity;
import wx.util.date.JsonDateDeserializer;
import wx.util.date.JsonDefaultDateSerializer;

import java.util.Date;

/**
 * auther: kiven on 2018/8/24/024 18:07
 * try it bast!
 */
public class SystemLog extends BasicEntity {
    /* 日志id */
    private String id;
    /* 操作人id */
    private String userInfo;
    /* 操作的ip */
    private String ip;
    /* 执行的方法 */
    private String exctionMethod;

    private OperatorType operatorType;

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

    public enum OperatorType {
        INSERT("插入", "insert"),
        UPDATE("修改", "update"),
        DELETE("删除", "delete"),
        EXCEPTION("异常", "exception");

        private String description;

        private String text;

        private OperatorType(String description, String text) {
            this.text = text;
            this.description = description;
        }

        public String getText() {
            return text;
        }

        public String getDescription() {
            return description;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
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

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }
}
