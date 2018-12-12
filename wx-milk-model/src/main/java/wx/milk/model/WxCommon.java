package wx.milk.model;

import wx.security.BasicEntity;

public class WxCommon extends BasicEntity {

    /* 各模块排序用 */
    private String sequence;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
