package wx.milk.security;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import wx.milk.model.User;
import wx.milk.model.UserModule;


public final class Authorization implements InitializingBean {

	// 获取相关属性
	@Autowired
	Environment env;

	public static final String SESSION_ID_KEY = "_s";


	// 获取对象
	public static User getUser() {
		User user = new User();
		user.setName("张健");
		user.setStatus((short)1);
		return user;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
//		String val = env.getProperty("server.session-timeout","3000");
	}

	public static List<UserModule> getUserModule(String appNo) {
		List<UserModule> list = new ArrayList<UserModule>();
		return list;
	}

	/**
	 * 是否为管理员
	 *
	 * @param roles
	 * @return
	 */
	public static boolean isAdmin(List<String> roles) {
		return true;
	}

	public static boolean isAdmin() {
		return isAdmin(getUserRole());
	}

	public static List<String> getUserRole() {
		List<String> roles = new ArrayList<String>();

		return roles;
	}

	public static void signOut() {
	/*	clear();
		_inc.getTokenProvider().clear();
		_inc.provider.signOut();*/
	}
}
