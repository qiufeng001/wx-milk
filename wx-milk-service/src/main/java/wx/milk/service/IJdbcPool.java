package wx.milk.service;

import wx.base.service.IService;
import wx.security.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface IJdbcPool extends IService<User, String> {
    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * 获取连接池的数量
     */
    int getConnectionSize();
}
