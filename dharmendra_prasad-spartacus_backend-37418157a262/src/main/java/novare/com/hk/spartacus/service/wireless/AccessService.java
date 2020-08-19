package novare.com.hk.spartacus.service.wireless;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import novare.com.hk.spartacus.Repo.ModuleRepository;
import novare.com.hk.spartacus.Repo.RoleRepository;
import novare.com.hk.spartacus.Repo.TeamModuleRoleLinkRepository;
import novare.com.hk.spartacus.Repo.TeamRepository;
import novare.com.hk.spartacus.Repo.UsersRepository;
import novare.com.hk.spartacus.domain.Module;
import novare.com.hk.spartacus.domain.Role;
import novare.com.hk.spartacus.domain.Team;
import novare.com.hk.spartacus.domain.TeamModuleRoleLink;
import novare.com.hk.spartacus.domain.Users;
import novare.com.hk.spartacus.model.AccessDetailDTO;
import novare.com.hk.spartacus.model.ModuleDTO;
import novare.com.hk.spartacus.model.RoleDTO;
import novare.com.hk.spartacus.model.TeamDTO;

@Service
public class AccessService {

	@Autowired
	private TeamRepository repo;

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UsersRepository userRepo;

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private TeamModuleRoleLinkRepository linkRepo;

	public List<TeamDTO> getAllTeams() {
		List<TeamDTO> teams = new ArrayList<TeamDTO>();
		Iterable<Team> allTeams = repo.findAll();
		Iterable<Role> allRoles = roleRepo.findAll();
		Iterable<Module> allModules = moduleRepo.findAll();
		TeamDTO teamDTO = null;
		for (Team team : allTeams) {
			for (Module module : allModules) {
				List<RoleDTO> roleList = new ArrayList<RoleDTO>();
				ModuleDTO moduleDTO = new ModuleDTO(module.getId(), module.getName(), false);
				for (Role role : allRoles) {
					Set<TeamModuleRoleLink> teamModuleRoleLink = team.getTeamModuleRoleLink();
					RoleDTO roleDTO = new RoleDTO(role.getName(), role.getId(), false);
					for (TeamModuleRoleLink link : teamModuleRoleLink) {
						Module moduleFromLink = link.getModule();
						Role roleFromLink = link.getRole();
						if (moduleFromLink != null && roleFromLink != null && moduleFromLink.equals(module)
								&& roleFromLink.equals(role)) {
							roleDTO.setSelected(true);
							break;
						}
					}
					roleList.add(roleDTO);
				}

				teamDTO = new TeamDTO();
				teamDTO.setName(team.getName());
				teamDTO.setId(team.getTeamId());
				teamDTO.setModule(moduleDTO);
				roleList.sort(new Comparator<RoleDTO>() {

					@Override
					public int compare(RoleDTO o1, RoleDTO o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				teamDTO.setRoles(roleList.toArray(new RoleDTO[0]));
				teams.add(teamDTO);
			}
		}
		return teams;
	}

	public void updateTeam(TeamDTO[] teams) {
		List<Team> teamsToUpdate = new ArrayList<Team>();
		// remove all the links

		linkRepo.deleteAll();

		Iterable<Team> allTeams = repo.findAll();
		for (Team team : allTeams) {
			Set<TeamModuleRoleLink> teamModuleLink = new HashSet<TeamModuleRoleLink>();
			for (TeamDTO teamDTO : teams) {
				if (teamDTO.getName().equals(team.getName())) {
					for (RoleDTO roleDTO : teamDTO.getRoles()) {
						TeamModuleRoleLink link = new TeamModuleRoleLink();
						link.setTeam(team);
						link.setModule(moduleRepo.findById(teamDTO.getModule().getId()).orElse(null));
						if (roleDTO.isSelected()) {
							link.setRole(roleRepo.findById(roleDTO.getId()).orElse(null));
						}
						teamModuleLink.add(link);
					}
				}
			}
			team.setTeamModuleRoleLink(teamModuleLink);
			teamsToUpdate.add(team);
		}
		repo.saveAll(allTeams);
	}

	public AccessDetailDTO accessDetails(long id) {
		Optional<Users> user = userRepo.findById(id);
		AccessDetailDTO authDetail = new AccessDetailDTO();
		if (user.isPresent()) {
			Iterable<Module> allModules = user.get().getModules();
			Iterable<Role> allRoles = user.get().getRoles();
			List<String> roles = new ArrayList<>();
			for (Role role : allRoles) {
				roles.add(role.getName());
			}

			List<String> modules = new ArrayList<>();
			for (Module module : allModules) {
				modules.add(module.getName());
			}

			authDetail.setUser(user.get().getName());
			authDetail.setRoles(roles.toArray(new String[0]));
			authDetail.setModules(modules.toArray(new String[0]));
			authDetail.setAccessInformation(null);

		}

		return authDetail;
	}

	public List<TeamDTO> allTeams() {
		Iterable<Team> allTeams = repo.findAll();
		List<TeamDTO> teams = new ArrayList<TeamDTO>();
		for (Team team : allTeams) {
			TeamDTO teamDTO = new TeamDTO();
			teamDTO.setId(team.getTeamId());
			teamDTO.setName(team.getName());
			teams.add(teamDTO);
		}

		return teams;
	}

	public void addRole(RoleDTO roleDTO) {
		Role role = new Role();
		role.setName(roleDTO.getName());
		TeamDTO team = roleDTO.getTeam();
		Optional<Team> teamById = teamRepo.findById(team.getId());
		Iterable<Module> allModules = moduleRepo.findAll();
		Map<Long, Module> moduleMap = new HashMap<Long, Module>();
		for (Module mod : allModules) {
			moduleMap.put(mod.getId(), mod);
		}
		Set<TeamModuleRoleLink> links = new HashSet<TeamModuleRoleLink>();

		for (ModuleDTO dto : roleDTO.getModules()) {
			if (dto.isSelected()) {
				TeamModuleRoleLink link = new TeamModuleRoleLink();
				link.setModule(moduleMap.get(dto.getId()));
				link.setTeam(teamById.orElse(null));
				link.setRole(role);
				links.add(link);
			}
		}
		role.setTeamModuleUserLink(links);
		roleRepo.save(role);

	}

}
