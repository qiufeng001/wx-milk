package wx.query;

import java.io.Serializable;

/**
 * 排序
 * @author zhong.h
 *
 */
public class Sort  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7242789313829031835L;
	String name;
	boolean desc = true;
	
	public Sort(){
		
	}
	
	public Sort(String name,boolean desc){
		this.name = name;
		this.desc = desc;
	}
	
	public Sort(String name ){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDesc() {
		return desc;
	}
	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return name + (desc? " desc":"");
	}
	
	
}
