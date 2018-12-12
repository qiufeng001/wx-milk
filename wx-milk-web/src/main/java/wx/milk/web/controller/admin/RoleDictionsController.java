package wx.milk.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.base.domain.DataChangeEntry;
import wx.base.manager.IManager;
import wx.exception.JsonManagerException;
import wx.exception.manager.ManagerException;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.manager.admin.IRoleDictionsManager;
import wx.milk.manager.admin.IUserRoleManager;
import wx.milk.model.Dictions;
import wx.milk.model.RoleDictions;
import wx.milk.model.UserRole;
import wx.milk.web.utils.ControllerUtils;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;
import wx.util.ShiroUtils;

import java.util.List;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/roleDictions/*")
public class RoleDictionsController {

    @Autowired
    private IRoleDictionsManager manager;
    @Autowired
    private IUserRoleManager userRoleManager;
    @Autowired
    private IDictionsManager dictionsManager;

    protected IManager<RoleDictions, String> getManager() {
        return manager;
    }

    protected String getTempleteFolder() {
        return "";
    }

    @ResponseBody
    @RequestMapping("/dictions")
    public List<Dictions> getDictions() {
        List<UserRole> rolesList = userRoleManager.selectByParams(ControllerUtils.getQuery(new Query(), "account", ShiroUtils.getUser().getAccount()));
        return dictionsManager.selectByParams(ControllerUtils.getQuery(new Query(), "roles",  rolesList));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public RoleDictions findByPrimaryKey(@PathVariable String id) {
        return getManager().findByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public RoleDictions findByParam(Query query) throws JsonManagerException {
        return getManager().findByParam(query);
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResult<RoleDictions> selectByPage(Query query, Pagenation page) {

        long total = page.getTotal();
        if (total < 0) {
            total = getManager().selectCount(query);
        }
        List<RoleDictions> rows = getManager().selectByPage(query, page);
        return new PageResult<>(rows, total);
    }

    @ResponseBody
    @RequestMapping("/query")
    public List<RoleDictions> selectByParams(Query query) {
        return getManager().selectByParams(query);

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public RoleDictions create(RoleDictions entry) {
        getManager().insert(entry);
        return entry;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public RoleDictions update(RoleDictions entry) throws JsonManagerException {

        try {
            getManager().update(entry);
            return entry;
        } catch (ManagerException e) {
            throw new JsonManagerException(e);
        }
    }

    /**
     * user/delete?account=admin
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public Integer deleteByParams(Query query) {
        return getManager().deleteByParams(query);
    }

    /**
     * user/delete/1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
    public Integer deleteByPrimaryKey(@PathVariable("id") String id) {
        return getManager().deleteByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/batchsave")
    public Integer batchSave(DataChangeEntry<RoleDictions> datas) {
        return getManager().batchSave(datas.getInserted(), datas.getUpdated(), datas.getDeleted());
    }

}
