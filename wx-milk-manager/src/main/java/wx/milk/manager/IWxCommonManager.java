package wx.milk.manager;

import wx.base.manager.IManager;
import wx.exception.manager.ManagerException;
import wx.milk.model.WxCommon;
import wx.milk.model.WxJob;
import wx.query.Query;

public interface IWxCommonManager extends IManager<WxCommon, String> {

    WxCommon findMaxSequence(Query query) throws ManagerException;
}
