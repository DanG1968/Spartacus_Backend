package novare.com.hk.spartacus.model;

public class ModuleDTO {

	private long id;
	private String name;
	private RoleDTO[] roles;
	private boolean selected;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleDTO[] getRoles() {
		return roles;
	}

	public void setRoles(RoleDTO[] roles) {
		this.roles = roles;
	}

	public ModuleDTO() {
		super();
	}

	public ModuleDTO(long id, String name, boolean selected) {
		super();
		this.id = id;
		this.name = name;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "ModuleDTO [id=" + id + ", name=" + name + ", selected=" + selected + "]";
	}

}
