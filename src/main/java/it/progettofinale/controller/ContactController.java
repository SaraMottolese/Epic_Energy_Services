package it.progettofinale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.progettofinale.model.Contact;
import it.progettofinale.model.Invoice;
import it.progettofinale.service.ContactService;

@RestController
@RequestMapping("api/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@PutMapping("/update")
	public ResponseEntity<Contact> update(@RequestBody Contact contact){
		Contact contactUpdate= contactService.update(contact);
		return new ResponseEntity<Contact>(contactUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		contactService.delete(id);
		return new ResponseEntity<String>("Contatto eliminato correttamente", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<Contact>> findAll(Pageable page){
		Page<Contact> contactList= contactService.findAll(page);
		return new ResponseEntity<Page<Contact>>(contactList, HttpStatus.OK);
	}
	
/***************** FILTRI RICERCA *****************/

	@GetMapping("/id/{id}")
	public ResponseEntity<Contact> findById(@PathVariable Long id) {
		Contact contact= contactService.findById(id);
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	

}
