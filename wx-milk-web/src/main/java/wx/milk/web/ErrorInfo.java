package wx.milk.web;

public class ErrorInfo<T> {

    public static final Integer OK = 1;
    public static final Integer ERROR = 100;

    private Integer code;
    
    public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	private String message;
    private String url;
    private T data;

    // 省略getter和setter

}
