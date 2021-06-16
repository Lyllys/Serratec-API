package org.serratec.resources;

import java.util.List;

import org.serratec.dtos.pedido.PedidoCadastroDTO;
import org.serratec.entities.Pedido;
import org.serratec.entities.exceptions.PedidoException;
import org.serratec.repositories.ClientRepository;
import org.serratec.repositories.PedidoRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoResource {

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping("/pedidos")
	public ResponseEntity<?> getPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();

		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@PostMapping("/pedido/cadastrar")
	public ResponseEntity<?> postPedido(@RequestBody PedidoCadastroDTO dto) {
		Pedido pedido = dto.toPedido(clientRepository, produtoRepository);
		try {
			pedidoRepository.save(pedido);
			return new ResponseEntity<>("Pedido efetuado! Protocolo: " + pedido.getNumeroPedido(), HttpStatus.OK);
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
