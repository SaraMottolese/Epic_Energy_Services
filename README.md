# BE_ProgettoFinale
Progetto di fine corso back-end proposto da Epicode.
Realizzare un sistema CRM per un'azienda fornitrice di energia denominata Epic Energy Services che vuole gestire i contatti con i propri clienti business.
Il sistema, basato su Web Service REST, Spring Boot e database PostgreSQL, deve permettere di gestire un elenco dei clienti e le relative fatture.

Il sistema chiede un'autenticazione, i ruoli che uno utente può avere sono di tipo admin e di tipo user. Gli admin possono eseguire ogni tipo di modifica e cancellazione di dati mentre agli utenti di tipo user sono permessi soltanto le operazioni di lettura.


## Contenuti

- [Database](#Database)
- [Gestione dati](#gestione-dati)
- [Documentazione API](#Documentazione-API)
- [Test](#Test)

## Database
All'avvio del progetto il database (clienti, comuni, province) viene popolato attraverso dei file csv.

## Gestione dati
- Per la gestione dei dati ho scelto che un indirizzo, e un contatto non possano venir creati separatamente da un cliente. In quanto in una situazione come questa non hanno motivo di esistere al di fuori di un cliente. POssiamo avere un cliente senza indirizzo e senza contatto ma non un contatto o un indirizzo senza cliente.

- Non è possibile né eliminare né modificare i comuni e le province.

- Per una fattura è possibile modificare solo lo stato (pagata o non pagata) in quanto di solito i dati di una fattura emessa non possono essere modificata, in caso di errore la fattura viene annullata. 

##  Doucemtazione API
All'interno del progetto è possibile trovare la collection con le chiamate Rest di Postman.
Le API sono documemtate con OpenApi e Swagger.

## Test
Sono presenti dei test JUnit e MockMvc

## License
MIT ©
  
  




