package wx.milk.domain;

import com.framework.core.base.domain.IRepository;
import org.apache.ibatis.annotations.Mapper;
import wx.milk.model.Company;

/**
 * 用户接口
 * @author zhong.h
 */
@Mapper
public interface CompanyRepository extends IRepository<Company, String> {

}
