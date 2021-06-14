package org.serratec.dtos.client;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.serratec.dtos.endereco.EnderecoCadastroDTO;
import org.serratec.entities.Client;

import br.com.caelum.stella.bean.validation.CPF;

public class ClientCadastroDTO {
	
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String username;
	
	@NotNull
	@NotBlank
	private String senha;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@NotBlank
	@Size(min = 11, max =11)
	@CPF
	private String cpf;
	
	@NotNull
	@NotBlank
	private String telefone;
	
	@NotNull
	private LocalDate dataNascimento;
	
	private EnderecoCadastroDTO endereco;
	
	public Client toClient() {
		
		Client client = new Client();
		client.setEmail(this.email);
		client.setUsername(this.username);
		client.setSenha(this.senha);
		client.setNome(this.nome);
		client.setCpf(this.cpf);
		client.setTelefone(this.telefone);
		client.setDataNascimento(this.dataNascimento);
		client.setEndereco(endereco.toEndereco());
		
		return client;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnderecoCadastroDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoCadastroDTO endereco) {
		this.endereco = endereco;
	}

	
}
