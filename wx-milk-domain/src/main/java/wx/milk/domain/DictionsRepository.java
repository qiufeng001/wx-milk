package wx.milk.domain;

import com.framework.core.base.domain.IRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wx.milk.model.Dictions;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 */
@Mapper
public interface DictionsRepository extends IRepository<Dictions, String> {

    /**
     * 查询出该用户操作数据库的权限（增删改查权限）
     * @param params
     * @return
     */
    List<Dictions> selectDictions(@Param("params") Map<String, Object> params);
}
