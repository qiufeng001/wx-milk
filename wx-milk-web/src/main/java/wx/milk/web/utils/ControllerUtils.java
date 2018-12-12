package wx.milk.web.utils;

import wx.query.Query;
import wx.query.Statement;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14/014.
 */
public class ControllerUtils {

    public static Query getQuery(Query query, String... values) {
        Statement statement = new Statement();
        statement.setName(values[0]);
        statement.setValue(values[1]);
        query.and(statement);
        return query;
    }

    public static Query getQuery(Query query, String name,List... values) {
        Statement statement = new Statement();
        statement.setName(name);
        statement.setValue(values[0]);
        query.and(statement);
        return query;
    }
}
