package novare.com.hk.spartacus.model;

public class UserModule {

	private boolean active;
	private String username;
	private String email;
	private RoleDTO[] roles;
	private ModuleDTO[] modules;
	private long id;
	private TeamDTO[] teams;

	public UserModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleDTO[] getRoles() {
		return roles;
	}

	public void setRoles(RoleDTO[] roles) {
		this.roles = roles;
	}

	public ModuleDTO[] getModules() {
		return modules;
	}

	public void setModules(ModuleDTO[] modules) {
		this.modules = modules;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TeamDTO[] getTeams() {
		return teams;
	}

	public void setTeams(TeamDTO[] teams) {
		this.teams = teams;
	}

}
