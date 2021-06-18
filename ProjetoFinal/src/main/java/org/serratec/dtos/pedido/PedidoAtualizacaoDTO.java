package org.serratec.dtos.pedido;

import java.util.ArrayList;
import java.util.List;

import org.serratec.entities.Pedido;
import org.serratec.repositories.ClientRepository;
import org.serratec.repositories.ProdutoRepository;

public class PedidoAtualizacaoDTO {
	
	private List<PedidoProdutoCadastroDTO> produtos  = new ArrayList<>();
	private double valorPedido;
	//private Pagamento formaDePagamento
	
//	public Pedido toPedido(ClientRepository clientRepository ,ProdutoRepository produtoRepository ) {
//		Pedido pedido = new Pedido();
//		
//		
//	}

	public List<PedidoProdutoCadastroDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<PedidoProdutoCadastroDTO> produtos) {
		this.produtos = produtos;
	}

	public double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}
	
	

}
