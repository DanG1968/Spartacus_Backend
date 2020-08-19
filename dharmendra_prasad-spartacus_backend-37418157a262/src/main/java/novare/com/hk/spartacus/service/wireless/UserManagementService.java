package novare.com.hk.spartacus.service.wireless;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import novare.com.hk.spartacus.Repo.ModuleRepository;
import novare.com.hk.spartacus.Repo.RoleRepository;
import novare.com.hk.spartacus.Repo.TeamRepository;
import novare.com.hk.spartacus.Repo.UsersRepository;
import novare.com.hk.spartacus.domain.Module;
import novare.com.hk.spartacus.domain.Role;
import novare.com.hk.spartacus.domain.Team;
import novare.com.hk.spartacus.domain.Users;
import novare.com.hk.spartacus.model.ModuleDTO;
import novare.com.hk.spartacus.model.RoleDTO;
import novare.com.hk.spartacus.model.TeamDTO;
import novare.com.hk.spartacus.model.UserModule;

@Service
public class UserManagementService {

	@Autowired
	private UsersRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private TeamRepository teamRepo;

	public List<UserModule> allUserModules() {
		Iterable<Users> allUsers = repo.findAll();
		Iterable<Role> allRoles = roleRepo.findAll();
		Iterable<Module> allModules = moduleRepo.findAll();
		Iterable<Team> allTeams = teamRepo.findAll();

		List<UserModule> returnValue = new ArrayList<UserModule>();
		for (Users user : allUsers) {
			UserModule userRecord = createUserModule(user, allRoles, allModules, allTeams);
			returnValue.add(userRecord);
		}
		return returnValue;
	}

	public List<ModuleDTO> allModules() {
		List<ModuleDTO> modules = new ArrayList<ModuleDTO>();
		Iterable<Module> allModules = moduleRepo.findAll();
		for (Module module : allModules) {
			modules.add(new ModuleDTO(module.getId(), module.getName(), false));
		}

		return modules;
	}

	public List<String> allRoles() {
		List<String> roles = new ArrayList<String>();
		Iterable<Role> allRoles = roleRepo.findAll();
		for (Role role : allRoles) {
			roles.add(role.getName());
		}

		return roles;
	}

	private UserModule createUserModule(Users user, Iterable<Role> allRoles, Iterable<Module> allModules,
			Iterable<Team> allTeams) {
		UserModule userRecord = new UserModule();
		userRecord.setId(user.getUserID());
		userRecord.setEmail(user.getEmail());
		userRecord.setUsername(user.getName());

		List<RoleDTO> roleList = new ArrayList<RoleDTO>();
		List<ModuleDTO> moduleList = new ArrayList<ModuleDTO>();
		List<TeamDTO> teamList = new ArrayList<TeamDTO>();

		if (allRoles != null) {
			for (Role role : allRoles) {
				roleList.add(new RoleDTO(role.getName(), role.getId(), false));
			}

			for (Role role : user.getRoles()) {
				for (RoleDTO roleDTO : roleList) {
					if (roleDTO.getId() == role.getId() && roleDTO.getName().equals(role.getName())) {
						roleDTO.setSelected(true);
						break;
					}
				}
			}
		}

		if (allModules != null) {
			for (Module module : allModules) {
				moduleList.add(new ModuleDTO(module.getId(), module.getName(), false));
			}

			for (Module module : user.getModules()) {
				for (ModuleDTO moduleDTO : moduleList) {
					if (moduleDTO.getId() == module.getId() && moduleDTO.getName().equals(module.getName())) {
						moduleDTO.setSelected(true);
						break;
					}
				}
			}
		}

		if (allTeams != null) {
			for (Team team : allTeams) {
				teamList.add(new TeamDTO(team.getName(), team.getTeamId(), false));
			}

			for (Team team : user.getTeams()) {
				for (TeamDTO teamDTO : teamList) {
					if (teamDTO.getId() == team.getTeamId() && teamDTO.getName().equals(team.getName())) {
						teamDTO.setSelected(true);
						break;
					}
				}
			}
		}

		userRecord.setRoles(roleList.toArray(new RoleDTO[0]));
		userRecord.setModules(moduleList.toArray(new ModuleDTO[0]));
		userRecord.setTeams(teamList.toArray(new TeamDTO[0]));
		return userRecord;
	}

	public UserModule addUser(UserModule user) {

		Users newUser = new Users();
		newUser.setEmail(user.getEmail());
		newUser.setName(user.getUsername());

		Iterable<Role> allRoles = roleRepo.findAll();

		Set<Role> rolesForUser = new HashSet<Role>();
		for (Role role : allRoles) {
			if (role.getName().equals(user.getRoles()[0].getName())) {
				rolesForUser.add(role);
			}
		}

		Iterable<Team> allTeams = teamRepo.findAll();

		Set<Team> teamForUser = new HashSet<Team>();
		for (Team team : allTeams) {
			if (team.getName().equals(user.getTeams()[0].getName())) {
				teamForUser.add(team);
			}
		}
		newUser.setRoles(rolesForUser);
		newUser.setTeams(teamForUser);

		Users save = repo.save(newUser);
		return createUserModule(save, rolesForUser, null, teamForUser);
	}

	public void deleteUser(UserModule user) {
		long id = user.getId();
		repo.deleteById(id);

	}

	public void updateUsers(UserModule[] users) {
		try {
			List<Users> usersToUpdate = new ArrayList<Users>();
			Iterable<Role> allRoles = roleRepo.findAll();
			Iterable<Team> allTeams = teamRepo.findAll();
			Iterable<Module> allModules = moduleRepo.findAll();
			for (UserModule userModule : users) {
				Users user = repo.findById(userModule.getId()).orElse(null);
				if (user != null) {

					Set<Role> roles = user.getRoles();
					RoleDTO[] roleDTOArray = userModule.getRoles();

					// add all the roles which are selected
					for (RoleDTO roleDTO : roleDTOArray) {
						for (Role role : allRoles) {
							if (roleDTO.isSelected() && role.getId() == roleDTO.getId()) {
								roles.add(role);
								break;
							} else if (!roleDTO.isSelected() && role.getId() == roleDTO.getId()
									&& roles.contains(role)) {
								roles.remove(role);
							}
						}
					}

					Set<Team> teams = user.getTeams();
					TeamDTO[] teamDTOArray = userModule.getTeams();
					for (TeamDTO teamDTO : teamDTOArray) {
						for (Team team : allTeams) {

							if (teamDTO.isSelected() && team.getTeamId() == teamDTO.getId()) {
								teams.add(team);
								break;
							} else if (!teamDTO.isSelected() && team.getTeamId() == teamDTO.getId()
									&& teams.contains(team)) {
								teams.remove(team);
							}
						}
					}
					
					Set<Module> modules = user.getModules();
					ModuleDTO[] moduleDTOArray = userModule.getModules();
					for (ModuleDTO moduleDTO : moduleDTOArray) {
						for (Module module : allModules) {

							if (moduleDTO.isSelected() && module.getId() == moduleDTO.getId()) {
								modules.add(module);
								break;
							} else if (!moduleDTO.isSelected() && module.getId() == moduleDTO.getId()
									&& modules.contains(module)) {
								modules.remove(module);
							}
						}
					}
				}

				if (user != null) {
					usersToUpdate.add(user);
				}

			}

			if (!usersToUpdate.isEmpty()) {
				repo.saveAll(usersToUpdate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Users findUserByEmail(String email) {
		return repo.findByEmail(email);
	}

	
}
