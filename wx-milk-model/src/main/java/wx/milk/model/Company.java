package wx.milk.model;

import wx.security.BasicEntity;

/**
 *
 * @author zhong.h
 */
public class Company extends BasicEntity {
    private String name;
    private String zoneId;
    private String address;
    private String phoneNum;
    private String legalPerson;
    private String legalPersonNum;
    private String sessionInvalidTime;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonNum() {
        return legalPersonNum;
    }

    public void setLegalPersonNum(String legalPersonNum) {
        this.legalPersonNum = legalPersonNum;
    }

    public String getSessionInvalidTime() {
        return sessionInvalidTime;
    }

    public void setSessionInvalidTime(String sessionInvalidTime) {
        this.sessionInvalidTime = sessionInvalidTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
