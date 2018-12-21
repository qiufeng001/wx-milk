package wx.milk.domain;

import com.framework.model.Common;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.framework.core.base.domain.IRepository;

import java.util.Map;

/**
 * 公共模块
 */
@Mapper
public interface WxCommonRepository extends IRepository<Common, String> {

    /**
     * 查询出该用户操作数据库的权限（增删改查权限）
     * @param params
     * @return
     */
    Common findMaxSequence(@Param("params") Map<String, Object> params);
}
