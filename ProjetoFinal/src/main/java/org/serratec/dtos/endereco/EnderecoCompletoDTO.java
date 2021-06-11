package org.serratec.dtos.endereco;

import org.serratec.entities.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCompletoDTO {

	private String completo;

	public EnderecoCompletoDTO(Endereco endereco) {
		
		String uri = "https://viacep.com.br/ws/" + endereco.getCep() + "/json/";

	    RestTemplate rest = new RestTemplate();    
	    EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);
	    
	    this.completo = viaCep.getLogradouro() + ", " + endereco.getNumero() + ", " + endereco.getComplemento() + ", " + viaCep.getBairro() + ", " + viaCep.getLocalidade() + "/" + viaCep.getUf();
	}

	public String getCompleto() {
		return completo;
	}
	
}

