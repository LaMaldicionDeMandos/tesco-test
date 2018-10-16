package coop.tecso.examen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.dto.EchoMessage;

@RestController
public class EchoController {
	
    @PostMapping("/echo")
    public ResponseEntity<?> echo(@RequestBody EchoMessage message) {
    	
    	EchoMessage response = new EchoMessage();
    	response.setMensaje("Mensaje recibido: " + message.getMensaje());
    
    	return new ResponseEntity<EchoMessage>(response, HttpStatus.OK);
    
    }

}
