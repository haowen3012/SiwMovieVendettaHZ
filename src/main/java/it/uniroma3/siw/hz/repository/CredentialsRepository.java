package it.uniroma3.siw.hz.repository;

import java.util.Optional;

import it.uniroma3.siw.hz.model.Credentials;
import org.springframework.data.repository.CrudRepository;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	 Optional<Credentials> findByUsername(String username);

}