package wx.exception;

public class InvalidOperationException extends RuntimeException {

	private static final long serialVersionUID = -8252517370493850304L;

	public InvalidOperationException(Exception ex){
		super(ex);
	}
	
	public InvalidOperationException(String msg){
		super(msg);
	}
	
	public InvalidOperationException(String msg,Exception ex){
		super(msg,ex);
	}
	
	public InvalidOperationException(){
		super();
	}
}
