package it.progettofinale.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progettofinale.security.model.User;
import it.progettofinale.security.service.UserService;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/getById/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "trova User da id", description = "Il metodo permette di trovare un user conoscendo l'id")
	@ApiResponse(responseCode = "200", description = "User trovato")
	public ResponseEntity<User> findById(@PathVariable(required = true) Long id) {
		Optional<User> find = userService.findById(id);
		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "trova tutti gli User", description = "Il metodo ritorna la lista degli User")
	@ApiResponse(responseCode = "200", description = "Lista caricata")
	public ResponseEntity<Page<User>> getAll(Pageable page) {
		Page<User> userList = userService.findAll(page);
		return new ResponseEntity<Page<User>>(userList, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "elimina User", description = "Il metodo permette di eliminare un user conoscendo l'id")
	@ApiResponse(responseCode = "200", description = "user eliminato")
	public ResponseEntity<String> delete(@PathVariable Long id){
		Optional<User> user= userService.findById(id);
		if(user.isPresent()) {
			userService.delete(id);
			return new ResponseEntity<String>("Utente eliminato correttamente", HttpStatus.OK);
		}else
			return new ResponseEntity<String>("Non esistono utenti con questo id", HttpStatus.NOT_FOUND);
	
	}

}
