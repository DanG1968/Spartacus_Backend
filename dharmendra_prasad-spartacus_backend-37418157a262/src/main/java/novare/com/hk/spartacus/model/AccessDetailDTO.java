package novare.com.hk.spartacus.model;

import java.util.List;
import java.util.Map;

public class AccessDetailDTO {

	private String user;
	private String[] roles;

	private String[] modules;
	private Map<String, List<String>> accessInformation;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public Map<String, List<String>> getAccessInformation() {
		return accessInformation;
	}

	public void setAccessInformation(Map<String, List<String>> accessInformation) {
		this.accessInformation = accessInformation;
	}

	public String[] getModules() {
		return modules;
	}

	public void setModules(String[] modules) {
		this.modules = modules;
	}

}
