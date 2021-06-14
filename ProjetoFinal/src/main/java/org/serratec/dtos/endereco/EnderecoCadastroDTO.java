package org.serratec.dtos.endereco;

import org.serratec.entities.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCadastroDTO {

	private String cep;
	private Integer numero;
	private String complemento;
	
	public Endereco toEndereco() {
		
		String uri = "https://viacep.com.br/ws/" + cep + "/json/";

	    RestTemplate rest = new RestTemplate();    
	    EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);
		
		Endereco endereco = new Endereco();
		endereco.setCep(this.cep);
		endereco.setComplemento(this.complemento);
		endereco.setNumero(this.numero);
		endereco.setRua(viaCep.getLogradouro());
		endereco.setBairro(viaCep.getBairro());
		endereco.setCidade(viaCep.getLocalidade());
		endereco.setEstado(viaCep.getUf());
		
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
