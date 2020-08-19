package novare.com.hk.spartacus.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import novare.com.hk.spartacus.service.wireless.WirelessService;

@Controller
@RequestMapping("/api")
@CrossOrigin
public class WirelessController {
	@Autowired
	WirelessService wirelessService;

	@PostMapping("/wireless")
	public ResponseEntity<String> insert(@RequestBody MultipartFile file) throws IOException {
		return new ResponseEntity<>(wirelessService.insert(file), HttpStatus.OK);
	}

}
