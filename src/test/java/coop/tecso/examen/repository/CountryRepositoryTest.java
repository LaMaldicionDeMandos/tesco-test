package coop.tecso.examen.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import coop.tecso.examen.model.Country;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CountryRepositoryTest {


    @Autowired
    private CountryRepository countryRepository;
    
    @Before
    public void setUp() {
    	Country country = new Country();
    	country.setIsoCode("ISO_CODE_TEST");
    	country.setName("NAME_TEST");
    	
    	countryRepository.save(country);
    }
        
    @Test
    public void findAllMustReturnAllCountries() {
    	List<Country> result = countryRepository.findAll();
    	assertEquals(1, result.size());
    }
    
}
