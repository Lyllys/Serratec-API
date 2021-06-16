package org.serratec.repositories;



import java.util.Optional;

import org.serratec.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByCpf(String Cpf);

	Optional<Client> findByEmail(String email);


}
