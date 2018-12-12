package wx.exception;

public class ArgumentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8252517370493850304L;

	public ArgumentException(Exception ex){
		super(ex);
	}
	
	public ArgumentException(String msg){
		super(msg);
	}
	
	public ArgumentException(String msg,Exception ex){
		super(msg,ex);
	}
	
	public ArgumentException(){
		super();
	}
}
