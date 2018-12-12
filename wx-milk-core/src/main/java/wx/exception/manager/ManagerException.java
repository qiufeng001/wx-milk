package wx.exception.manager;

public class ManagerException extends RuntimeException {

	private static final long serialVersionUID = -1795121797342252073L;
	
	// 自定义异常信息
	public ManagerException(String msg) {
		super(msg);
	}
	
	// Excetpion 中的异常 
	public ManagerException(Exception e) {
		super(e);
	}
	
	// Excetpion 和 自定义的异常
	public ManagerException(String msg, Exception e) {
		super(msg,e);
	}
}
