package org.serratec.repositories;

import java.util.Optional;

import org.serratec.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByCodigo(String codigo);
		
}
