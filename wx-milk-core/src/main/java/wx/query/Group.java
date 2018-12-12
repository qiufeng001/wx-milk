package wx.query;

import java.io.Serializable;

/**
 * 分组 GroupBy
 * 
 * @author zhong.h
 */
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4554230434760693633L;
	
	/**
	 * 排序字段
	 */
	private String groupName;

	/**
	 * @param groupName
	 */
	public Group(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [groupName=").append(groupName).append("]");
		return builder.toString();
	}
}
