package wx.milk.service;

import com.framework.core.query.Query;
import com.framework.model.Common;
import com.framework.service.IService;

public interface IWxCommonService extends IService<Common, String> {
    Common findMaxSequence(Query query);
}
