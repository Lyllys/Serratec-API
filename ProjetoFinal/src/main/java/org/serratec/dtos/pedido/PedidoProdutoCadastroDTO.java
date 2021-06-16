package org.serratec.dtos.pedido;

import java.util.Optional;

import org.serratec.entities.PedidoProduto;
import org.serratec.entities.Produto;
import org.serratec.entities.exceptions.PedidoProdutoException;
import org.serratec.repositories.ProdutoRepository;

public class PedidoProdutoCadastroDTO {

	private String codigo;
	private Integer quantidade;
	
	public PedidoProduto toPedidoProduto(ProdutoRepository produtoRepository) {
		
		PedidoProduto pedidoProduto = new PedidoProduto();
		
		Optional<Produto> produto = produtoRepository.findByCodigo(codigo); 
		
		if(produto.isEmpty())
			throw new PedidoProdutoException ("Produto não encontrado.");
		
		pedidoProduto.setProduto(produto.get());
		pedidoProduto.setQuantidade(quantidade);
		pedidoProduto.setPreco(produto.get().getPreco());
		
		return pedidoProduto;
	}

	public String getCodigo() {
		return codigo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	
	
}
