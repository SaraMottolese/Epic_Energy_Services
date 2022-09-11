package it.progettofinale.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String businessName;
	Long vtaNumber;
	String email;
	String pec;
	String phoneNumber;
	String contactName;
	String contactSurname;
	String contactPhoneNumber;
	String contactEmail;
	Address operationalHeadquartersAddress;
	Address registeredOfficeAddress;
	Date insertionDate;
	Date lastContactDate;
	Double annualTurnOver;
	List<Invoice> invoices;
	

}
