package wx.log;

import java.lang.annotation.*;

/**
 * 定义注解，拦截service
 * auther: kiven on 2018/8/24/024 17:53
 * try it bast!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SystemServiceLog {
    //定义成员
    String description() default "" ;


}