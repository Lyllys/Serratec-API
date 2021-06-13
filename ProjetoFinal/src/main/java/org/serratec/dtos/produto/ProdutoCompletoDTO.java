package org.serratec.dtos.produto;

import java.time.LocalDateTime;

import org.serratec.dtos.categoria.CategoriaSimplificadoDTO;
import org.serratec.entities.Produto;

public class ProdutoCompletoDTO {

	private String nome;
	private String descricao;
	private Double preco;
	private Integer quantidadeEstoque;
	private LocalDateTime dataCadastro;
	private CategoriaSimplificadoDTO categoria;
	
	public ProdutoCompletoDTO(Produto produto) {
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.quantidadeEstoque = produto.getQuantidadeEstoque();
		this.dataCadastro = produto.getDataCadastro();
		this.categoria = new CategoriaSimplificadoDTO(produto.getCategoria());
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public CategoriaSimplificadoDTO getCategoria() {
		return categoria;
	}
}
