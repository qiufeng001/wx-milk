package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wx.base.domain.IRepository;
import wx.milk.model.Dictions;
import wx.milk.model.WxCommon;

import java.util.List;
import java.util.Map;

/**
 * 公共模块
 */
@Mapper
public interface WxCommonRepository extends IRepository<WxCommon, String> {

    /**
     * 查询出该用户操作数据库的权限（增删改查权限）
     * @param params
     * @return
     */
    WxCommon findMaxSequence(@Param("params") Map<String, Object> params);
}
