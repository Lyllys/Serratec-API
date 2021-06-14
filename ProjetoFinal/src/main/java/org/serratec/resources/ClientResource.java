package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dtos.client.ClientCadastroDTO;
import org.serratec.dtos.client.ClientCompletoDTO;
import org.serratec.entities.Client;
import org.serratec.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientResource {

	@Autowired
	ClientRepository repository;

	@GetMapping("/clients")
	public ResponseEntity<?> getClients() {
		List<Client> todos = repository.findAll();
		List<ClientCompletoDTO> dtos = new ArrayList<>();

		for (Client c : todos) {
			dtos.add(new ClientCompletoDTO(c));
		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping("/clients/cadastrar")
	public ResponseEntity<?> cadastrarClients(@Validated @RequestBody ClientCadastroDTO dto) {
		Client client = dto.toClient();
		try {
			repository.save(client);
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
		}
	}
}
