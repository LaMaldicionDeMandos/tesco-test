package coop.tecso.examen.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import coop.tecso.examen.model.Country;
import coop.tecso.examen.repository.CountryRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private CountryController controller;
	    
    @MockBean
    private CountryRepository myRepository;
    
    
    @Test
    public void findAllWithEmptyResult() throws Exception {
    	
    	when(myRepository.findAll()).thenReturn(Collections.emptyList());
    	
    	String root = controller.getClass().getAnnotation(RequestMapping.class).value()[0];
        
    	mvc.perform(get(root +"/findAll"))
    							.andDo(print())
    							.andExpect(status().isOk())
    							.andExpect(jsonPath("$", hasSize(0)))
    							.andReturn();	
    }
    
    @Test
    public void findAllWithOneResultElement() throws Exception {
    	
    	Long id = 1L;
    	String isoCode = "XXX";
    	String name = "ZZZZZZ";
    	
    	Country element = new Country();
    	element.setId(id);
    	element.setIsoCode(isoCode);
    	element.setName(name);
    	
    	when(myRepository.findAll()).thenReturn(Arrays.asList(element));
    	
    	String root = controller.getClass().getAnnotation(RequestMapping.class).value()[0];
        
    	mvc.perform(get(root +"/findAll"))
    							.andDo(print())
    							.andExpect(status().isOk())
    							.andExpect(jsonPath("$", hasSize(1)))
    							.andExpect(jsonPath("$[0].id", is(id.intValue())))
    							.andExpect(jsonPath("$[0].isoCode", is(isoCode)))
    							.andExpect(jsonPath("$[0].name", is(name)))
    							.andReturn();	
    }
	
}
