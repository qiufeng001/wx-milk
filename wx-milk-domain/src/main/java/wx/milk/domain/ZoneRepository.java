package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import com.framework.core.base.domain.IRepository;
import wx.milk.model.Zone;

/**
 * 用户接口
 * @author zh
 */
@Mapper
public interface ZoneRepository extends IRepository<Zone, String> {

}
