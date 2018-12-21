package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import com.framework.core.base.domain.IRepository;
import wx.milk.model.RoleDictions;

/**
 * 用户接口
 */
@Mapper
public interface RoleDictionsRepository extends IRepository<RoleDictions, String> {

}
