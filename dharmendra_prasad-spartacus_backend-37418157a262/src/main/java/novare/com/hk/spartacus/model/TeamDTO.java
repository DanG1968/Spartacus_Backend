package novare.com.hk.spartacus.model;

public class TeamDTO {
	private String name;
	private long id;
	private ModuleDTO module;
	private RoleDTO[] roles;
	private boolean selected;

	public TeamDTO() {
		super();
	}

	public TeamDTO(String name, Long teamId, boolean b) {
		this.name = name;
		this.id = teamId;
		this.selected = b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ModuleDTO getModule() {
		return module;
	}

	public void setModule(ModuleDTO module) {
		this.module = module;
	}

	public RoleDTO[] getRoles() {
		return roles;
	}

	public void setRoles(RoleDTO[] roles) {
		this.roles = roles;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
