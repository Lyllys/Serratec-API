package org.serratec.repositories;

import java.util.Optional;

import org.serratec.entities.PedidoProduto;
import org.serratec.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, Long> {

	Optional<PedidoProduto> findByProduto(Produto existente);

}
