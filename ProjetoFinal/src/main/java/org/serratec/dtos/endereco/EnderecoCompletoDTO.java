package org.serratec.dtos.endereco;

import org.serratec.entities.Endereco;

public class EnderecoCompletoDTO {

	private String completo;
	
	public EnderecoCompletoDTO(Endereco endereco) {
	    this.completo = endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getComplemento() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + "/" + endereco.getEstado();
	}

	public String getCompleto() {
		return completo;
	}
	
}

