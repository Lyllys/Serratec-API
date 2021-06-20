package org.serratec.dtos.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.dtos.endereco.EnderecoCadastroDTO;
import org.serratec.entities.Client;

public class ClientAtualizacaoDTO {
	
	@NotNull
	@NotBlank
	private String telefone;
	
	private EnderecoCadastroDTO endereco;
	
	public Client toClient() {
		
		Client client = new Client();
		client.setTelefone(this.telefone);
		client.setEndereco(endereco.toEndereco());
		
		return client;
		
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public EnderecoCadastroDTO getEndereco() {
		return endereco;
	}
	
	public void setEndereco(EnderecoCadastroDTO endereco) {
		this.endereco = endereco;
	}

}
