package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.pedido.PedidoAtualizacaoDTO;
import org.serratec.dtos.pedido.PedidoCadastroDTO;
import org.serratec.dtos.pedido.PedidoCompletoDTO;
import org.serratec.entities.Pedido;
import org.serratec.entities.PedidoProduto;
import org.serratec.entities.Produto;
import org.serratec.entities.exceptions.PedidoException;
import org.serratec.repositories.ClientRepository;
import org.serratec.repositories.PedidoRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	        List<Pedido> todos = pedidoRepository.findAll();
	        List<PedidoCompletoDTO> dtos = new ArrayList<>();

	        for (Pedido p : todos) {
	                dtos.add(new PedidoCompletoDTO(p));

	        }
	        return new ResponseEntity<>(dtos, HttpStatus.OK);

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
	//TODO Falta fazer a verificação pra só deixar editar um pedido EM_ABERTO
	@PutMapping("pedido/atualizar/{numeroPedido}")
	public ResponseEntity<?> putPedido(@PathVariable String numeroPedido, @RequestBody PedidoAtualizacaoDTO atualizado){
		
		Optional <Pedido> opcional = pedidoRepository.findByNumeroPedido(numeroPedido);
		
		if(opcional.isEmpty())
			return new ResponseEntity<>("Pedido não encontrado.", HttpStatus.BAD_REQUEST);
		
		Pedido pedidoAtualizado = atualizado.toPedido(produtoRepository, opcional.get());
		
		try {
			pedidoRepository.save(pedidoAtualizado);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Pedido não pode ser atualizado.", HttpStatus.BAD_REQUEST);
		}
			
		return new ResponseEntity<>(new PedidoCompletoDTO(pedidoAtualizado), HttpStatus.OK);
	}
	
}