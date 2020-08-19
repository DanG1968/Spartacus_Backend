package novare.com.hk.spartacus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import novare.com.hk.spartacus.domain.Users;
import novare.com.hk.spartacus.model.AccessDetailDTO;
import novare.com.hk.spartacus.model.RoleDTO;
import novare.com.hk.spartacus.model.TeamDTO;
import novare.com.hk.spartacus.service.wireless.AccessService;
import novare.com.hk.spartacus.service.wireless.UserManagementService;

@Controller
@RequestMapping("/api/accessmgmt")
@CrossOrigin
public class AccessController {
	@Autowired
	private AccessService service;

	@Autowired
	private UserManagementService userService;

	@GetMapping("/teams")
	public ResponseEntity<List<TeamDTO>> fetchTeams() {
		List<TeamDTO> allTeams = service.getAllTeams();
		return new ResponseEntity<List<TeamDTO>>(allTeams, HttpStatus.OK);
	}
	
	@GetMapping("/allTeams")
	public ResponseEntity<List<TeamDTO>> fetchAllTeams() {
		List<TeamDTO> allTeams = service.allTeams();
		return new ResponseEntity<List<TeamDTO>>(allTeams, HttpStatus.OK);
	}

	@PutMapping("/updateTeams")
	public ResponseEntity<Void> updateTeams(@RequestBody TeamDTO[] teams) {
		service.updateTeam(teams);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/addRole")
	public ResponseEntity<Void> addRole(@RequestBody RoleDTO user) {
		service.addRole(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<AccessDetailDTO> fetchAccessInformation(Authentication authentication) {
		String email = authentication.getName();
		Users user = userService.findUserByEmail(email);
		AccessDetailDTO authDetail = service.accessDetails(user.getUserID());
		return new ResponseEntity<AccessDetailDTO>(authDetail, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<String> genericException(Exception ex) {
		return new ResponseEntity<String>(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
