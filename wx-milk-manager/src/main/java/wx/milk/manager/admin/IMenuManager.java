package wx.milk.manager.admin;

import wx.base.manager.IManager;
import wx.milk.model.Menu;
import wx.query.Query;

import java.util.List;


public interface IMenuManager extends IManager<Menu, String> {

    /**
     * 根据用户的账号查询该用户可以看到的菜单
     *
     * @return 菜单集合
     */
    public List<Menu> selectByAccount(Query query);

    /**
     * 查询pid
     * @return
     */
    List<Menu> selectPid();
}
