package it.progettofinale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progettofinale.model.Contact;
import it.progettofinale.service.ContactService;

@RestController
@RequestMapping("/api/contact")
@SecurityRequirement(name = "bearerAuth")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "update del contatto", description = "il metodo permette di cambiare i dati di un contatto "
			+ "esistente")
	@ApiResponse(responseCode = "200", description = "contatto aggiornato")
	public ResponseEntity<Contact> update(@RequestBody Contact contact){
		Contact contactUpdate= contactService.update(contact);
		return new ResponseEntity<Contact>(contactUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "elimina contatto", description = "Il metodo permette di eliminare un contatto conoscendo l'id")
	@ApiResponse(responseCode = "200", description = "contatto eliminato")
	public ResponseEntity<String> delete(@PathVariable Long id){
		contactService.delete(id);
		return new ResponseEntity<String>("Contatto eliminato correttamente", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista dei contatti", description = "Il metodo ritorna indietro la lista completa dei contatti")
	@ApiResponse(responseCode = "200", description = "Lista dei comuni acquisita")
	public ResponseEntity<Page<Contact>> findAll(Pageable page){
		Page<Contact> contactList= contactService.findAll(page);
		return new ResponseEntity<Page<Contact>>(contactList, HttpStatus.OK);
	}
	
/***************** FILTRI RICERCA *****************/

	@GetMapping("/id/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "trova contatto da id", description = "Il metodo permette di trovare un contatto conoscendo l'id")
	@ApiResponse(responseCode = "200", description = "contatto trovato")
	public ResponseEntity<Contact> findById(@PathVariable Long id) {
		Contact contact= contactService.findById(id);
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	

}
