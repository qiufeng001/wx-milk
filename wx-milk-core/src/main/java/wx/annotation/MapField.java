package wx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface MapField {
	
	String value() default "";
	
	/**
	 * 是否必填
	 * @return
	 */
	boolean required() default false;
	
	/**
	 * orderUnit,mall,lookupentry
	 * @return
	 */
	String dataType() default "";
	
	String converter() default "";
	
	String formatter() default "";
}
