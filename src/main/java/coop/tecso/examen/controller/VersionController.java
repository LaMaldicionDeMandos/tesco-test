package coop.tecso.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/version")
public class VersionController {
	
	@Autowired
	BuildProperties buildProperties;
	
    @GetMapping("/number")
    public ResponseEntity<?> getVersion() {
    	
        return new ResponseEntity<String>(buildProperties.getVersion(), HttpStatus.OK);
    
    }
    
}
