package org.serratec.repositories;

import java.util.List;
import java.util.Optional;

import org.serratec.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByNomeIgnoreCase(String nome);

	List<Produto> findByNomeContainingIgnoreCase(String nome);

	

}
