package org.serratec.dtos.endereco;

import org.serratec.entities.Endereco;

public class EnderecoCadastroDTO {

	private String cep;
	private Integer numero;
	private String complemento;
	
	public Endereco toEndereco() {
		
		Endereco endereco = new Endereco();
		endereco.setCep(this.cep);
		endereco.setComplemento(this.complemento);
		endereco.setNumero(this.numero);
		
		return endereco;
		
	}
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
}
