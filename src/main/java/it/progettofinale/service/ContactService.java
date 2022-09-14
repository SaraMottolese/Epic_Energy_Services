package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.progettofinale.exception.ContactException;
import it.progettofinale.model.Contact;
import it.progettofinale.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	public void add(Contact contact) {
		contactRepository.save(contact);
	}
	
	public Contact update(Contact contact) {
		Optional<Contact> contactResult= contactRepository.findById(contact.getId());
		if(contactResult.isPresent()) {
			Contact contactUpdate= contactResult.get();
			if(contact.getEmail()!=null)
				contactUpdate.setEmail(contact.getEmail());
			if(contact.getName()!=null)
				contactUpdate.setName(contact.getName());
			if(contact.getSurname()!=null)
				contactUpdate.setSurname(contact.getSurname());
			if(contact.getPhoneNumber()!=null)
				contactUpdate.setPhoneNumber(contact.getPhoneNumber());
			return contactRepository.save(contactUpdate);
		}else
			throw new ContactException("Impossibile aggiornare il record del contatto in quanto non presente nel database");
	}
	
	public void delete(Long id) {
		Optional<Contact> contactResult= contactRepository.findById(id);
		if(contactResult.isPresent())
			contactRepository.deleteById(id);
		else
			throw new ContactException(
					"Impossibile eliminare il record del contatto in quanto non presente nel database");
	}
	
	public Page<Contact> findAll(Pageable page){
		return contactRepository.findAll(page);
	}
	
	
	public Contact findById(Long id) {
		Optional<Contact> contactResult= contactRepository.findById(id);
		if(contactResult.isPresent()) {
			Contact contact= contactResult.get();
			return contact;
		}else
			throw new ContactException("Contatto non trovato");
	}

}
