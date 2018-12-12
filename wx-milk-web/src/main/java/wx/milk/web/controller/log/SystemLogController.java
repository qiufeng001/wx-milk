package wx.milk.web.controller.log;

import java.lang.annotation.*;

/**
 * 定义注解。拦截controlelr
 * auther: kiven on 2018/8/24/024 17:49
 * try it bast!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //定义了注解声明在哪些元素之前
@Documented
public @interface SystemLogController {

    //定义成员
    String descrption() default "" ;//描述
    String actionType() default "" ;//操作的类型，1、添加 2、修改 3、删除 4、查询

}
