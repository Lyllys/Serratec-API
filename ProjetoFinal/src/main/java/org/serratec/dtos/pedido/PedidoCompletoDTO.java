package org.serratec.dtos.pedido;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dtos.endereco.EnderecoCompletoDTO;
import org.serratec.entities.Pedido;
import org.serratec.entities.PedidoProduto;
import org.serratec.entities.enums.StatusPedido;

public class PedidoCompletoDTO {
	
	private String numeroPedido;
    private String nomeCliente;
    private EnderecoCompletoDTO endereco;
    private List<PedidoProdutoSimplificadoDTO> itens = new ArrayList<>(); 
    private double valorTotal;
    private StatusPedido status;

    public PedidoCompletoDTO  (Pedido pedido) {
        this.numeroPedido = pedido.getNumeroPedido();
        this.nomeCliente = pedido.getClient().getNome();
        this.endereco = new EnderecoCompletoDTO(pedido.getClient().getEndereco());
        this.status = pedido.getStatus();

        for (PedidoProduto dto : pedido.getProdutos()) {
            this.itens.add(new PedidoProdutoSimplificadoDTO(dto));
        }
        this.valorTotal = pedido.getValorTotal();

    }

	public StatusPedido getStatus() {
		return status;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public EnderecoCompletoDTO getEndereco() {
		return endereco;
	}

	public List<PedidoProdutoSimplificadoDTO> getItens() {
		return itens;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	

}
