package org.serratec.dtos.categoria;

import org.serratec.entities.Categoria;

public class CategoriaSimplificadoDTO {

	private String nome;

	public CategoriaSimplificadoDTO(Categoria categoria) {
		this.nome = categoria.getNome();
	}
	
	public String getNome() {
		return nome;
	}

}
