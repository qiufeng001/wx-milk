package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wx.base.domain.IRepository;
import wx.milk.model.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单接口
 */
@Mapper
public interface MenuRepository extends IRepository<Menu, String> {

    /**
     * 根据用户的账号查询该用户可以看到的菜单
     *
     * @param params
     * @return 菜单集合
     */
    public List<Menu> selectByAccount(@Param("params") Map<String, Object> params);

    /**
     * 查询pid
     * @return
     */
    List<Menu> selectPid();
}
