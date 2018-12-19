package wx.milk.domain;

import org.apache.ibatis.annotations.Mapper;
import wx.base.domain.IRepository;
import wx.milk.model.Company;
import wx.milk.model.Zone;

/**
 * 用户接口
 * @author zhong.h
 */
@Mapper
public interface CompanyRepository extends IRepository<Company, String> {

}
