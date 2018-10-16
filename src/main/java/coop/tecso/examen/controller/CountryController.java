package coop.tecso.examen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coop.tecso.examen.dto.CountryDto;
import coop.tecso.examen.model.Country;
import coop.tecso.examen.repository.CountryRepository;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryRepository countryRepository;
	
	// Get All Countries
	@GetMapping("/findAll")
	public List<CountryDto> findAll() {
		
		List<CountryDto> result = new ArrayList<>();
		for (Country entity : countryRepository.findAll()) {
			CountryDto dto = new CountryDto();
			dto.setId(entity.getId());
			dto.setIsoCode(entity.getIsoCode());
			dto.setName(entity.getName());
			
			result.add(dto);
		}
		
	    return result;
	}
	
}
