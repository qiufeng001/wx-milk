package wx.milk.manager.admin;

import com.framework.core.query.Query;
import com.framework.manager.IManager;
import wx.milk.model.Dictions;


import java.util.List;


public interface IDictionsManager extends IManager<Dictions, String> {

    /**
     * 查询出该用户操作数据库的权限（增删改查权限）
     * @return
     */
    List<Dictions> selectDictions(Query query);
}
