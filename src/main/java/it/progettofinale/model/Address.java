package it.progettofinale.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Address {
	
	String  street;
	Integer streetNumber;
	String district; //localita'
	String  zipCode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne//(cascade = { CascadeType.DETACH,CascadeType.REFRESH
   // }, fetch = FetchType.EAGER)
	City city; //Comune

}
