package org.serratec.dtos.pedido;

import java.util.ArrayList;
import java.util.List;

import org.serratec.entities.Pedido;
import org.serratec.entities.PedidoProduto;
import org.serratec.entities.enums.Pagamento;
import org.serratec.entities.enums.StatusPedido;
import org.serratec.repositories.ProdutoRepository;

public class PedidoAtualizacaoDTO {
	
	private List<PedidoProdutoCadastroDTO> produtos = new ArrayList<>();
	private Pagamento formaDePagamento;
	private StatusPedido status;
	
	//TODO adicionar o pedidoProdutoRepository para fazer um findByPedidoId e criar a lógica de adicionar/remover quantidade
	public Pedido toPedido(ProdutoRepository produtoRepository, Pedido pedido) {
		
		pedido.setFormaDePagamento(formaDePagamento);
		pedido.setStatus(status);
		
		for (PedidoProdutoCadastroDTO p : produtos) {
			PedidoProduto pedidoProduto = p.toPedidoProduto(produtoRepository);
			pedidoProduto.setPedido(pedido);
			pedido.getProdutos().add(pedidoProduto);
		}
		pedido.calcularValorTotal();
		
		return pedido;
	}
	

	public List<PedidoProdutoCadastroDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<PedidoProdutoCadastroDTO> produtos) {
		this.produtos = produtos;
	}

	public Pagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(Pagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}


	public StatusPedido getStatus() {
		return status;
	}


	public void setStatus(StatusPedido status) {
		this.status = status;
	}

}
