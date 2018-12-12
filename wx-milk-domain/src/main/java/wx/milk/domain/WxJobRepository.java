package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import wx.base.domain.IRepository;
import wx.milk.model.WxJob;

/**
 * Created by Administrator on 2018/6/14/014.
 */
@Mapper
public interface WxJobRepository extends IRepository<WxJob, String> {

}
