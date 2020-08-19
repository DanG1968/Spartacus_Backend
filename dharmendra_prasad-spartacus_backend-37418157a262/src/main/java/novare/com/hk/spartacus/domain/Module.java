package novare.com.hk.spartacus.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Module {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String name;

	@OneToMany(mappedBy = "module", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TeamModuleRoleLink> teamModuleUserLink;

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

	public Module() {
		super();
	}

	public Set<TeamModuleRoleLink> getTeamModuleUserLink() {
		return teamModuleUserLink;
	}

	public void setTeamModuleUserLink(Set<TeamModuleRoleLink> teamModuleUserLink) {
		this.teamModuleUserLink = teamModuleUserLink;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + "]";
	}

	
}
