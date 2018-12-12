package wx.milk.web.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.base.controller.BaseController;
import wx.base.manager.IManager;
import wx.contants.WxConstant;
import wx.milk.manager.IWxCommonManager;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.admin.IMenuManager;
import wx.milk.model.Dictions;
import wx.milk.model.Menu;
import wx.milk.model.WxCommon;
import wx.milk.web.utils.RedisUtils;
import wx.query.Query;
import wx.query.Statement;
import wx.util.ShiroUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 这个是用于维护用户信息的类
 * 
 * @author zhong.h
 *	
 * try it,do it best!
 */

@Controller
@RequestMapping("/menu/*")
public class MenuController extends BaseController<Menu, String> {

    @Autowired
    private IMenuManager manager;
    @Autowired
    private IDictionsManager dictionsManager;
    @Autowired
    private IWxCommonManager commonManager;

    @Override
    protected IManager<Menu, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/admin/menu";
    }

    @RequestMapping("/selectDictionsByAccount")
    @ResponseBody
    public List<Dictions> selectDictions() {
        Statement statement = new Statement();
        statement.setName("account");
        statement.setValue(ShiroUtils.getUser().getAccount());
        Query query = new Query();
        query.where(statement);
        return dictionsManager.selectDictions(query);
    }

    @RequestMapping("/selectPid")
    @ResponseBody
    public List<Menu> selectPid() {
        return manager.selectPid();
    }

    @RequestMapping("/menuRole")
    @ResponseBody
    public Map<String, Object> menuRole() {
        Map<String, Object> map = new HashMap<>();

        map.put("kkk", "ja");
        return map;
    }

    /**
     * 编辑或者修改查询,跳转到编辑页面
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id") String id) {
        Menu menu = new Menu();
        if (!StringUtils.isEmpty(id)) {
            menu = manager.findByPrimaryKey(id);
        }
        model.addAttribute(menu);
        return "/admin/menu/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createOrUpdate")
    @ResponseBody
    public Menu createOrUpdate(Query query, Menu menu) {
        try {
            String id = menu.getId();
            if (StringUtils.isEmpty(id)) {
                // 新增
                if(menu.getPId() == null || menu.getPId().equalsIgnoreCase("")) {
                    menu.setPId("0");
                }
                menu.setSequence(RedisUtils.getAndUpdateSequence(query, commonManager,
                        WxConstant.MENU_SEQUENCE, "t_menu"));
                manager.insert(menu);
            } else {
                // 修改
                manager.update(menu);
            }
        } catch (Exception e) {
            logger.error(e);
            // return new JsonResult(false, JsonResult.FAILED);
        }
        return menu;
    }

    @RequestMapping("/validate")
    @ResponseBody
    public Integer validate(Query query) {
       // Map<String, Object> map = query.asMap();
        //String type = (String)map.get("type");
        Integer count = 0;
        try {
            count = manager.selectCount(query);
        }catch (Exception e) {
            logger.error(e);
        }
        return count;
    }

    @RequestMapping("/getSequence")
    @ResponseBody
    public WxCommon getSequence(Query query) {
        Statement statement = new Statement();
        statement.setName("tableName");
        statement.setValue("t_menu");
        WxCommon wxCommon = commonManager.findMaxSequence(query.and(statement));
        return wxCommon;
    }
}
