package wx.milk.model;

import com.framework.core.security.BasicEntity;

/**
 * Created by Administrator on 2018/6/14/014.
 */
public class Dictions extends BasicEntity {
    private String type;
    private String name;
    private String dictionsCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDictionsCode() {
        return dictionsCode;
    }

    public void setDictionsCode(String dictionsCode) {
        this.dictionsCode = dictionsCode;
    }
}
