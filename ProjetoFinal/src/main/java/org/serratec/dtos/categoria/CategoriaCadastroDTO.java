package org.serratec.dtos.categoria;

import org.serratec.entities.Categoria;
import org.serratec.repositories.ProdutoRepository;

public class CategoriaCadastroDTO {

	private String nome;
	private String descricao;
	
	public Categoria toCategoria(ProdutoRepository produtoRepository) {
		Categoria categoria = new Categoria();

		categoria.setNome(this.nome);
		categoria.setDescricao(this.descricao);

		return categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
