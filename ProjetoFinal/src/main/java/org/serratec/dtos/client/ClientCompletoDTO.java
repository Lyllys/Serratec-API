package org.serratec.dtos.client;

import org.serratec.dtos.endereco.EnderecoCompletoDTO;
import org.serratec.entities.Client;

public class ClientCompletoDTO {
	
	private String email;
	private String nome;
	private String cpf;
	private String telefone;
	private EnderecoCompletoDTO endereco;
	
	public ClientCompletoDTO(Client client) {
		this.email = client.getEmail();
		this.nome = client.getNome();
		this.cpf = client.getCpf();
		this.telefone = client.getTelefone();
		this.setEndereco(new EnderecoCompletoDTO(client.getEndereco()));
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public EnderecoCompletoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoCompletoDTO endereco) {
		this.endereco = endereco;
	}
}
