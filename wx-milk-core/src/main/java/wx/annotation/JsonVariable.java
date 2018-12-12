package wx.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonVariable {
	String value() default "";
	Class<?> type() default Object.class;
}
