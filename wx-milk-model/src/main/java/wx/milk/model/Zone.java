package wx.milk.model;

import wx.security.BasicEntity;

/**
 *
 * @author zhong.h
 */
public class Zone extends BasicEntity {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

