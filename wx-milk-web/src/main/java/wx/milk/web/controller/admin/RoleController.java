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
import wx.milk.manager.admin.IRoleManager;
import wx.milk.model.Role;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;

import java.util.List;

/**
 *
 *
 * @author Kevin
 *         <p>
 *         try it,do it best!
 */
@Controller
@RequestMapping("/role/*")
public class RoleController {

    @Autowired
    private IRoleManager manager;

    protected String getTempleteFolder() {
        return "";
    }

    protected IManager<Role, String> getManager() {
        return manager;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")

    public Role findByPrimaryKey(@PathVariable String id) {
        return getManager().findByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public Role findByParam(Query query) throws JsonManagerException {
        return getManager().findByParam(query);
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Role> selectByPage(Query query, Pagenation page) {

        long total = page.getTotal();
        if (total < 0) {
            total = getManager().selectCount(query);
        }
        List<Role> rows = getManager().selectByPage(query, page);
        return new PageResult<Role>(rows, total);
    }

    @ResponseBody
    @RequestMapping("/query")
    public List<Role> selectByParams(Query query) {
        return getManager().selectByParams(query);

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public Role create(Role entry) {
        getManager().insert(entry);
        return entry;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public Role update(Role entry) throws JsonManagerException {

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
    public Integer batchSave(DataChangeEntry<Role> datas) {
        return getManager().batchSave(datas.getInserted(), datas.getUpdated(), datas.getDeleted());
    }
}
