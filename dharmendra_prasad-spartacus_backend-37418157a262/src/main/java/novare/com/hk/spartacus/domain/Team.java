package novare.com.hk.spartacus.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamId;
	private String name;

	@OneToMany(mappedBy = "team", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TeamModuleRoleLink> teamModuleRoleLink;

	public Team() {
		super();
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TeamModuleRoleLink> getTeamModuleRoleLink() {
		return teamModuleRoleLink;
	}

	public void setTeamModuleRoleLink(Set<TeamModuleRoleLink> teamModuleRoleLink) {
		this.teamModuleRoleLink = teamModuleRoleLink;
	}

}
