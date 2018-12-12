package wx.milk.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.milk.service.IJdbcPool;
import wx.milk.web.utils.JDBCPoolUtils;
import wx.security.JsonResult;

import java.sql.Connection;

@Controller
public class JDBCPoolController {

    @Autowired
    private IJdbcPool pool;
    private Connection c1;
    private Connection c2;
    private Connection c3;

    @RequestMapping("/jdbcpool")
    @ResponseBody
    public JsonResult getJDBCPoolInfo() {
        JsonResult result = new JsonResult();
        try {
            c1 = pool.getConnection();
            c2 = pool.getConnection();
            result.setMessage("前获取连接数" + pool.getConnectionSize() + ";" + "后获取连接数" + pool.getConnectionSize());
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/jdbcpool_test")
    @ResponseBody
    public JsonResult getJDBCPoolInfoTest() {
        JsonResult result = new JsonResult();
        try {
            c1 = pool.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("数据库繁忙，请稍后再试！");
        }
        return result;
    }

    @RequestMapping("/releaseJDBCPool")
    @ResponseBody
    public JsonResult releaseJDBCPool() {
        JsonResult result = new JsonResult();
        try {

            //关闭连接
            if (c1 != null) {
                c1.close();
            }
            if (c2 != null) {
                c2.close();
            }
            if (c3 != null) {
                c3.close();
            }
            result.setMessage("前获取连接数" + pool.getConnectionSize());
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("数据库繁忙，请稍后再试！");
        }

        return result;
    }
}
