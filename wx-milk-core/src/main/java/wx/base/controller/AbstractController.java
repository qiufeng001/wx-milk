package wx.base.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 此抽象类，是控制层跳转路径的定义
 * 
 * 其中 抽象方法protected abstract String getTempleteFolder(); 目的是获取自定义的路径
 * 
 * @author Kevin
 *	
 * try it,do it best!
 */
public abstract class AbstractController {

	protected Log logger = LogFactory.getLog(getClass());

	protected abstract String getTemplateFolder();

	@RequestMapping(value = "/")
	public String index() {
		return getTemplateFolder() + "/index";
	}
}
