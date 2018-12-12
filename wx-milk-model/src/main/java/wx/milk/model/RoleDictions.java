package wx.milk.model;

import wx.security.BasicEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14/014.
 */
public class RoleDictions extends BasicEntity {
    private String dictionsId;
    private String roleNo;
    private List<Dictions> dictions;

    public String getDictionsId() {
        return dictionsId;
    }

    public void setDictionsId(String dictionsId) {
        this.dictionsId = dictionsId;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public List<Dictions> getDictions() {
        return dictions;
    }

    public void setDictions(List<Dictions> dictions) {
        this.dictions = dictions;
    }
}
