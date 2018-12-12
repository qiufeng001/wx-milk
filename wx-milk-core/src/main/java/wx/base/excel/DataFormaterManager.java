package wx.base.excel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class DataFormaterManager implements BeanPostProcessor {
	private final static Map<String, IDataFormater> map = new HashMap<>();

	public static IDataFormater get(String name) {
		return map.get(name);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof IDataFormater) {
			map.put(beanName, (IDataFormater) bean);
		}
		return bean;
	}

}
