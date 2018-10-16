package coop.tecso.examen.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.service.MathService;

@RestController
@RequestMapping("/math")
public class MathController {

	@Autowired
	private MathService mathService;
	
	@GetMapping("/sum")
	public BigDecimal sum(@RequestParam List<BigDecimal> valores) {
		
		return mathService.sumar(valores.toArray(new BigDecimal[valores.size()]));
		
	}
	
}
