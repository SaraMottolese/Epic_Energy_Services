package it.progettofinale.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

	String companyName;
	Long vtaNumber;
	String email;
	String pec;
	String phoneNumber;
	
	Double revenue;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	Address operationalHeadquartersAddress;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	Address registeredOfficeAddress;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate lastContactDate;

	@OneToMany(mappedBy = "customer",cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	List<Invoice> invoices;

	@Enumerated(EnumType.STRING)
	CustomerType type;
	
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	Contact contact;

}
