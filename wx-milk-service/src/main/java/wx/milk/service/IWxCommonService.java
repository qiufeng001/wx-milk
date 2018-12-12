package wx.milk.service;

import org.apache.catalina.Manager;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;
import wx.milk.model.WxCommon;
import wx.query.Query;

public interface IWxCommonService extends IService<WxCommon, String> {

    WxCommon findMaxSequence(Query query);
}
