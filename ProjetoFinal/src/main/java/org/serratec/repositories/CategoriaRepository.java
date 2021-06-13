package org.serratec.repositories;

import java.util.Optional;

import org.serratec.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Categoria findByNomeContainingIgnoreCase(String nome);

	Optional<Categoria> findByNome(String categoria);


		
}
