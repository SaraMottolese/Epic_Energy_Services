package it.progettofinale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import it.progettofinale.model.Address;
import it.progettofinale.repository.AddressRepository;

public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public void add(Address address) {
		addressRepository.save(address);
	}
	
	public Optional <Address> findByCityId(Long id){
		return addressRepository.findByCity_Id(id);
	}

}
