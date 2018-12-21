package wx.milk.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
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
