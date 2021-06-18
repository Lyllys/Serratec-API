package org.serratec.dtos.pedido;

import org.serratec.entities.PedidoProduto;

public class PedidoProdutoSimplificadoDTO {
	
	private String codigoProduto;
    private Double preco;
    private Integer quantidade;

    public PedidoProdutoSimplificadoDTO (PedidoProduto pedidoProduto) {
        this.codigoProduto= pedidoProduto.getProduto().getCodigo();
        this.preco = pedidoProduto.getPreco();
        this.quantidade = pedidoProduto.getQuantidade();

    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

}
