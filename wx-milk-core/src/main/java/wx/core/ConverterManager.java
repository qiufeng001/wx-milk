package wx.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ConverterManager implements BeanPostProcessor {

	static final Map<String, IConverter> maps = new HashMap<>();

	public static IConverter get(String name){
		return maps.get(name);
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof IConverter) {
			maps.put(beanName, (IConverter) bean);
		}
		return bean;
	}

}
