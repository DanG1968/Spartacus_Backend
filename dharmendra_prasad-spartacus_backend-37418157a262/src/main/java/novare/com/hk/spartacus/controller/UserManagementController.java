package novare.com.hk.spartacus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import novare.com.hk.spartacus.model.ModuleDTO;
import novare.com.hk.spartacus.model.UserModule;
import novare.com.hk.spartacus.service.wireless.UserManagementService;

@Controller
@RequestMapping("/api/management")
@CrossOrigin
public class UserManagementController {

	@Autowired
	private UserManagementService service;

	@GetMapping("/users")
	public ResponseEntity<List<UserModule>> fetchUsers() {
		List<UserModule> allUserModules = service.allUserModules();
		return new ResponseEntity<List<UserModule>>(allUserModules, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<UserModule> addUser(@RequestBody UserModule user) {
		UserModule addUser = service.addUser(user);
		return new ResponseEntity<UserModule>(addUser, HttpStatus.OK);
	}
	

	@PostMapping("/deleteUser")
	public ResponseEntity<UserModule> deleteUser(@RequestBody UserModule user) {
		service.deleteUser(user);
		return new ResponseEntity<UserModule>(user, HttpStatus.OK);
	}
	
	@PutMapping("/updateUsers")
	public ResponseEntity<Void> updateUsers(@RequestBody UserModule[] users) {
		service.updateUsers(users);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<String>> fetchRoles() {
		List<String> allRoles = service.allRoles();
		return new ResponseEntity<List<String>>(allRoles, HttpStatus.OK);
	}

	@GetMapping("/modules")
	public ResponseEntity<List<ModuleDTO>> fetchModules() {
		List<ModuleDTO> allModules = service.allModules();
		return new ResponseEntity<List<ModuleDTO>>(allModules, HttpStatus.OK);
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<String> genericException(Exception ex) {
		return new ResponseEntity<String>(ex.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
