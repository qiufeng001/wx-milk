package wx.milk.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wx.milk.manager.admin.IUserManager;
import wx.milk.service.IJdbcPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class JDBCPoolUtils {

    @Autowired
    private IJdbcPool pool;
    @Autowired
    private IUserManager manager;

    /**
     * 从池中获取一个连接
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    /**
     * 关闭连接
     *
     * @param conn
     * @param st
     * @param rs
     * @throws SQLException
     */
    public void CloseConnection(Connection conn, Statement st, ResultSet rs) throws SQLException {

        // 关闭存储查询结果的ResultSet对象
        if (rs != null) {
            rs.close();
        }

        //关闭Statement对象
        if (st != null) {
            st.close();
        }

        //关闭连接
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 获取连接池的数据库连接数量
     */
    public int getConnectionSize() {
        manager.getByAccount("account");

        return pool.getConnectionSize();
    }
}
