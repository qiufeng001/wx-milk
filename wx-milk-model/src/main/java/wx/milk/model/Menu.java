package wx.milk.model;

import wx.security.BasicEntity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhong.h
 */
public class Menu extends BasicEntity {
    // 菜单名称
    private String name;
    // 父菜单名称
    private String fName;
    // 父菜单id
    private String pId;
    // 菜单url
    private String url;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    // 菜单图标
    private String img;
    // 菜单状态
    private int status;
    // 菜单顺序
    private String sequence;
    // 子菜单
    private List<Menu> childMenus;
    // ... 省去getter和setter方法以及toString方法


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }
}
