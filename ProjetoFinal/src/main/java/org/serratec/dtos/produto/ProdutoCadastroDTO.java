package org.serratec.dtos.produto;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.entities.Categoria;
import org.serratec.entities.Produto;
import org.serratec.entities.ProdutoException;
import org.serratec.repositories.CategoriaRepository;

public class ProdutoCadastroDTO {

	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	@NotNull
	private Double preco;
	
	@NotNull
	private Integer quantidadeEstoque;
	
	private LocalDateTime dataCadastro;
	//private String imagem;
	
	@NotNull
	private String categoria;
	
	public Produto toProduto(CategoriaRepository categoriaRepository) {
		
		Produto produto = new Produto();
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setPreco(this.preco);
		produto.setQuantidadeEstoque(this.quantidadeEstoque);
		produto.setDataCadastro(LocalDateTime.now());
		
		Optional<Categoria> categoria = categoriaRepository.findByNome(this.categoria);
		
		if(categoria.isEmpty()) {
			throw new ProdutoException("Categoria " + this.categoria + " não encontrada.");
		}
		
		produto.setCategoria(categoria.get());
		
		return produto;
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

	public String getCategoria() {
		return categoria;
	}

	
	
	
}
