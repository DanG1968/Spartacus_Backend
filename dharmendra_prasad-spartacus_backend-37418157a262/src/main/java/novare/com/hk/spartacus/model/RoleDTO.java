package novare.com.hk.spartacus.model;

public class RoleDTO {

	private String name;
	private long id;
	private boolean selected;
	private TeamDTO team;
	private ModuleDTO[] modules;

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

	public RoleDTO(String name, long id, boolean selected) {
		super();
		this.name = name;
		this.id = id;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public TeamDTO getTeam() {
		return team;
	}

	public void setTeam(TeamDTO team) {
		this.team = team;
	}

	public ModuleDTO[] getModules() {
		return modules;
	}

	public void setModules(ModuleDTO[] modules) {
		this.modules = modules;
	}

	@Override
	public String toString() {
		return "RoleDTO [name=" + name + ", id=" + id + ", selected=" + selected + "]";
	}

	public RoleDTO() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleDTO other = (RoleDTO) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
