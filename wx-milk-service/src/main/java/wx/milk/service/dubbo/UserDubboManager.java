package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.framework.core.query.Query;
import com.framework.core.query.Statement;
import com.framework.core.security.User;

import org.springframework.beans.factory.annotation.Autowired;
import wx.milk.api.server.IJaegerTestProvider;
import wx.milk.api.server.IUserDubboProvider;
import wx.milk.service.admin.IUserService;



@Service
public class UserDubboManager implements IUserDubboProvider {

    @Autowired
    private IUserService userService;

    @Reference
    private IJaegerTestProvider jaegerTestProvider;

    @Override
    public User findByParam(String parentId) {
        try {

//            Span spanA = tracer.buildSpan("findByParam").start();

            Query query =  new Query();
            try{
                Thread.sleep(1500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            User user = userService.findByParam(getQ(query));
//            spanA.setTag("methodName", "IUserService.findByParam");
//            spanA.log("第一次调用！");

//            SpanContext context = spanA.context();
//            user.setRemark(test.getStr("这是第二个调用！"));
//            spanA.finish();

//            jaegerTestProvider.getStr("测试");
            return user;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Query getQ(Query query) {
        Statement statement = new Statement();
        statement.setName("account");
        statement.setValue("faker");
        query.and(statement);
        return query;
    }
}
