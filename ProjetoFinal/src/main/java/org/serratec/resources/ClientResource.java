package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.client.ClientAtualizacaoDTO;
import org.serratec.dtos.client.ClientCadastroDTO;
import org.serratec.dtos.client.ClientCompletoDTO;
import org.serratec.entities.Client;
import org.serratec.entities.exceptions.ViaCepException;
import org.serratec.repositories.ClientRepository;
import org.serratec.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientResource {

	@Autowired
	ClientRepository repository;
	
	@Autowired
    EmailService emailService;

	@GetMapping("/clients")
	public ResponseEntity<?> getClients() {
		List<Client> todos = repository.findAll();
		List<ClientCompletoDTO> dtos = new ArrayList<>();

		for (Client c : todos) {
			if(c.isEnabled()) {
				dtos.add(new ClientCompletoDTO(c));
			}
		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping("/clients/cadastrar")
	public ResponseEntity<?> cadastrarClients(@Validated @RequestBody ClientCadastroDTO dto) {
		Client client = null;
		try {
			client = dto.toClient();
			repository.save(client);
			emailService.enviar("Bem vindo", "Seu e-mail foi cadastrado com sucesso", client.getEmail());
			return new ResponseEntity<>("Cadastro concluído com sucesso!", HttpStatus.OK);

		} catch (DataIntegrityViolationException e) {
			if (repository.existsByEmail(client.getEmail())) {
				return new ResponseEntity<>("E-mail já cadastrado", HttpStatus.BAD_REQUEST);
			} else if (repository.existsByUsername(client.getUsername())) {
				return new ResponseEntity<>("Username já cadastrado", HttpStatus.BAD_REQUEST);
			} else if (repository.existsByCpf(client.getCpf())) {
				return new ResponseEntity<>("CPF já cadastrado", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ViaCepException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/client/atualizar/{username}")
	public ResponseEntity<?> atualizarClient(@PathVariable String username, @RequestBody ClientAtualizacaoDTO atualizado){
		
		Optional<Client> optional = repository.findByUsername(username);
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Cliente não existe", HttpStatus.NOT_FOUND);
		}
		
		try {
			Client client = optional.get();
			
			Client modificado = atualizado.toClient();
			
			if(modificado.getEndereco() != null) {
				client.setEndereco(modificado.getEndereco());
			}
			if(modificado.getTelefone() != null) {
				client.setTelefone(modificado.getTelefone());
			}
			
			repository.save(client);
		} catch (Exception e) {
			return new ResponseEntity<>("Cliente não pôde ser atualizado", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Cliente atualizado com sucesso", HttpStatus.OK);
	}
	
	@DeleteMapping("/client/deletar/{username}")
	public ResponseEntity<?> deleteClient(@PathVariable String username){
		
		Optional<Client> optional = repository.findByUsername(username);
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Cliente não existe", HttpStatus.NOT_FOUND);
		}
		
		try {
			Client client = optional.get();

			client.setEnabled(false);
			repository.save(client);
			
		} catch (Exception e) {
			return new ResponseEntity<>("Cliente não pôde ser atualizado", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Cliente deletado com sucesso", HttpStatus.OK);
	}
	
}
