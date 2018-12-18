package wx.milk.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import wx.base.service.IService;
import wx.milk.manager.ISystemLogManager;
import wx.milk.model.log.SystemLog;
import wx.milk.service.ISystemLogService;

public class SystemLogManagerImpl extends BaseManager<SystemLog, String> implements ISystemLogManager {

    @Autowired
    private ISystemLogService service;

    @Override
    protected IService<SystemLog, String> getService() {
        return service;
    }


}
