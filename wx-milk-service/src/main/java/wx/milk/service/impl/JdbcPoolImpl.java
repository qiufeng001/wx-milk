package wx.milk.service.impl;

import org.springframework.stereotype.Service;
import wx.base.domain.IRepository;
import wx.base.service.impl.BaseService;
import wx.milk.service.IJdbcPool;
import wx.security.User;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;
/*

@Service
public class JdbcPoolImpl extends BaseService<User, String> implements IJdbcPool, DataSource {

    @Override
    protected IRepository<User, String> getRepository() {
        return null;
    }

    //使用LinkedList集合存放数据库连接
    private static LinkedList<Connection> connPool = new LinkedList<Connection>();

    //在静态代码块中加载配置文件
    static {
        InputStream in = JdbcPoolImpl.class.getClassLoader().getResourceAsStream("application-dev.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String driver = prop.getProperty("db.driver");
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            //数据库连接池的初始化连接数的大小
            int InitSize = Integer.parseInt(prop.getProperty("db.initSize"));
            //加载驱动
            Class.forName(driver);
            for (int i = 0; i < InitSize; i++) {
                Connection conn = DriverManager.getConnection(url, user, password);
                //将创建的连接添加的list中
                connPool.add(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connPool.size() > 0) {
            //从集合中获取一个连接
            final Connection conn = connPool.removeFirst();
            //返回Connection的代理对象
            return (Connection) Proxy.newProxyInstance(JdbcPoolImpl.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if (!"close".equals(method.getName())) {
                        return method.invoke(conn, args);
                    } else {
                        connPool.add(conn);
                        System.out.println("关闭连接，实际还给了连接池");
                        System.out.println("池中连接数为 " + connPool.size());
                        return null;
                    }
                }
            });
        } else {
            throw new RuntimeException("数据库繁忙，稍后再试");
        }
    }

    @Override
    public int getConnectionSize() {
        int size = 0;
        try {
            size = connPool.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
*/
