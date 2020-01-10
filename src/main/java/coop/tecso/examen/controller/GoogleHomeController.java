package coop.tecso.examen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoogleHomeController {
    private final static Logger LOG = LoggerFactory.getLogger(GoogleHomeController.class);
	
    @PostMapping("/smarthome")
    public ResponseEntity<?> sync(@RequestBody Map<String, Object> body, @RequestHeader Map<String, String> headers) {
        LOG.info("Entrando al sync --> Body: {} --> headers: {}", body, headers);
        String response = "ok";
    	return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
