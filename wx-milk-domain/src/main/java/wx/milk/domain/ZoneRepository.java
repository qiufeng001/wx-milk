package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wx.base.domain.IRepository;
import wx.milk.model.Dictions;
import wx.milk.model.Zone;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * @author zh
 */
@Mapper
public interface ZoneRepository extends IRepository<Zone, String> {

}
