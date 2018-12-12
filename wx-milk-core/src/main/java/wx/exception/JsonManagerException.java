package wx.exception;

public class JsonManagerException extends RuntimeException {
	
	private static final long serialVersionUID = -1164558787443709220L;

	public JsonManagerException(String msg) {
		super(msg);
	}
	
	public JsonManagerException(Exception e) {
		super(e);
	}
	
	public JsonManagerException(String msg, Exception e) {
		super(msg, e);
	}
}
