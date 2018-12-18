package wx.milk.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.base.manager.impl.BaseManager;
import wx.base.service.IService;
import wx.milk.manager.ISystemLogManager;
import wx.milk.model.log.SystemLog;
import wx.milk.service.ISystemLogService;

@Service
public class SystemLogManagerImpl extends BaseManager<SystemLog, String> implements ISystemLogManager {

    @Autowired
    private ISystemLogService service;

    @Override
    protected IService<SystemLog, String> getService() {
        return service;
    }


}
