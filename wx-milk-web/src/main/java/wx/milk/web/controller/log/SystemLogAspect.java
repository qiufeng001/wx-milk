package wx.milk.web.controller.log;

import com.framework.core.configuration.Config;
import com.framework.core.util.JsonUtil;
import com.framework.core.util.ShiroUtils;
import com.framework.core.util.WebUtils;
import com.framework.web.inspect.ExecutionContext;
import com.framework.web.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wx.milk.manager.ISystemLogManager;
import wx.milk.model.log.SystemLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 定义日志切入类
 * <p>
 * 这里重点是 ThreadLocal 的使用
 * auther: kiven on 2018/8/24/024 17:55
 * try it bast!
 */
@Order(5)
@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private ISystemLogManager systemLogManager;
    private HttpServletRequest request;

    /**
     * 定义service切入点拦截规则，拦截SystemServiceLog注解的方法
     */
    @Pointcut("execution(* com.framework.manager.impl.BaseManager.*(..))")
    public void exceptionAspect() {
    }

    /**
     * 插入日志
     */
    @Pointcut("execution(* com.framework.manager.impl.BaseManager.insert(..))")
    public void insert() {

    }

    /**
     * 修改日志
     */
    @Pointcut("execution(* com.framework.manager.impl.BaseManager.update(..))")
    public void update() {

    }

    /**
     * 删除日志
     */
    @Pointcut("execution(* com.framework.manager.impl.BaseManager.delete*(..))")
    public void delete() {

    }

    @AfterReturning(value = "insert()")
    public void insertLog(JoinPoint joinPoint) throws Throwable {
        String pointCutName = ExecutionContext.getContextMap().get(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID));
        if (StringUtils.isBlank(pointCutName)) {
            ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), SystemLog.OperatorType.INSERT.getText());
        }
        // 判断参数
        boolean flag = (ExecutionContext.getContextMap().get(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID)))
                .equalsIgnoreCase(SystemLog.OperatorType.INSERT.getText());

        if (flag) {
            ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), SystemLog.OperatorType.STOP.getText());
            request = getHttpServletRequest();
            if (joinPoint.getArgs() == null) {// 没有参数
                return;
            }
            // 获取方法名
            String methodName = joinPoint.getSignature().getName();
            // 获取操作内容
            String opContent = optionContent(joinPoint.getArgs(), methodName);
            SystemLog log = new SystemLog();
            log.setIp(WebUtils.getRequestIp(request));
            log.setExctionMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setOperatorType(SystemLog.OperatorType.INSERT);
            log.setExecuteDate(new Date());
            log.setOperateUser(JsonUtil.obj2Json(ShiroUtils.getUser(), false));
            log.setParams(opContent);
            systemLogManager.insert(log);
        }
    }

    @AfterReturning(value = "delete()")
    public void deleteLog(JoinPoint joinPoint) throws Throwable {
        request = getHttpServletRequest();
        ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), SystemLog.OperatorType.DELETE.getText());

        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = optionContent(joinPoint.getArgs(), methodName);

        SystemLog log = new SystemLog();
        log.setExecuteDate(new Date());
        log.setExctionMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        log.setOperatorType(SystemLog.OperatorType.DELETE);
        log.setOperateUser(JsonUtil.obj2Json(ShiroUtils.getUser(), false));
        log.setParams(opContent);
        log.setIp(WebUtils.getRequestIp(request));
        systemLogManager.insert(log);
    }

    @AfterReturning(value = "update()")
    public void updateLog(JoinPoint joinPoint) throws Throwable {
        request = getHttpServletRequest();
        if (!(request.getRequestURI()).contains("/signout")) {
            ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), SystemLog.OperatorType.UPDATE.getText());

            // 判断参数
            if (joinPoint.getArgs() == null) {// 没有参数
                return;
            }
            // 获取方法名
            String methodName = joinPoint.getSignature().getName();
            request.setAttribute(Config.JOINT_POINT_METHOD_NAME, methodName);
            // 获取操作内容
            String opContent = optionContent(joinPoint.getArgs(), methodName);

            SystemLog log = new SystemLog();
            log.setExecuteDate(new Date());
            log.setIp(WebUtils.getRequestIp(request));
            log.setExctionMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setOperatorType(SystemLog.OperatorType.UPDATE);
            log.setOperateUser(JsonUtil.obj2Json(ShiroUtils.getUser(), false));
            log.setParams(opContent);
            systemLogManager.insert(log);
        }
    }

    /**
     * 异常日志
     *
     * @param joinPoint
     * @param e
     */
   // @AfterThrowing(pointcut = "exceptionAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        request = getHttpServletRequest();
        Map<String, String> map = ExecutionContext.getContextMap();
        if (map == null) {

        }
        ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), SystemLog.OperatorType.EXCEPTION.getText());

        // 获取登陆用户信息
        String user = RedisUtils.getUserJsonByToken(request);
        // 获取请求ip
        String ip = WebUtils.getRequestIp(request);
        // 获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            params = Arrays.toString(args);
        }
        try {
            SystemLog log = new SystemLog();
            log.setDescription(getServiceMthodDescription(joinPoint, e));
            log.setExceptionCode(e.getClass().getName());
            log.setOperatorType(SystemLog.OperatorType.EXCEPTION);
            log.setExceptionDetail(e.getClass().getName() + ": " + e.getMessage());
            log.setExctionMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setParams(params);
            log.setOperateUser(user);
            log.setExecuteDate(new Date());
            log.setIp(ip);
            // 保存数据库
            systemLogManager.insert(log);
        } catch (Exception ex) {
            e.printStackTrace();
        }

    }

    /**
     * 使用Java反射来获取被拦截方法(insert、update、delete)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            Method[] methods = info.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                rs.append("(" + methodName + ":" + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint, Exception e) throws Exception {
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if (clazzs.length != arguments.length) {
                continue;
            }
            description = e.getLocalizedMessage();
        }
        return description;
    }

    public HttpServletRequest getHttpServletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
}
