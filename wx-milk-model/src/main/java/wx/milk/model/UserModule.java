package wx.milk.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserModule  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3136680837180141281L;
	
	
	@Override
	public String toString() {
		return "UserModule [name=" + name + ", moduleNo=" + moduleNo + ", code=" + code + ", operations=" + operations
				+ ", roleNo=" + roleNo + ", url=" + url + ", ops=" + ops + "]";
	}

	private String name;
	private String moduleNo;
	private String code;
	private String operations; 
	private String roleNo;
	private String url;
	
	public void setOperations(String operations) {
		this.operations = operations;
		ops.clear();
		if(StringUtils.isNotEmpty(operations)){
			String[] vals = operations.split(",");
			for (int i = 0; i < vals.length; i++) {
				ops.add(vals[i]);
			}
		}
	}
	
	List<String> ops = new ArrayList<String>();
	public List<String> operationList(){
		return ops;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOperations() {
		return operations;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getOps() {
		return ops;
	}

	public void setOps(List<String> ops) {
		this.ops = ops;
	}
}
