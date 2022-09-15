package it.progettofinale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.progettofinale.model.Address;
import it.progettofinale.model.City;
import it.progettofinale.model.Contact;
import it.progettofinale.model.Customer;
import it.progettofinale.model.CustomerType;
import it.progettofinale.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CustomerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void addCustomer() throws Exception {
		City city= new City();
		city.setName("Roma");
		
		Address address = new Address();
		address.setCity(city);
		address.setDistrict("Ostia");
		address.setStreet("Via delle baleniere");
		address.setStreetNumber(24);
		address.setZipCode("00121");
		
		Contact contact= new Contact();
		contact.setEmail("pinco@pallo.com");
		contact.setName("Pinco");
		contact.setPhoneNumber("234567898");
		contact.setSurname("Pallo");
		
		
		Customer customer= new Customer();
		customer.setCompanyName("Ciccio Pasticcio");
		customer.setContact(contact);
		customer.setEmail("ciccio@pasticcio.com");
		customer.setOperationalHeadquartersAddress(address);
		customer.setPec("pinco@pec.it");
		customer.setPhoneNumber("268938746");
		customer.setRegisteredOfficeAddress(address);
		customer.setType(CustomerType.SRL);
		customer.setVtaNumber(198273665L);
		//customerService.add(customer);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(customer);
		
		MvcResult result = mockMvc
				.perform(post("/api/customer/add").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(content().json("{'companyName':'Ciccio Pasticcio'}")).andReturn();

		log.info(json);
		
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void deleteCustomer() throws Exception {
		this.mockMvc.perform(delete("/api/customer/delete/1")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void getAllCustomer() throws Exception{
		this.mockMvc.perform(get("/api/customer/getall")).andExpect(status().isOk());
	}
	
	
	

}
