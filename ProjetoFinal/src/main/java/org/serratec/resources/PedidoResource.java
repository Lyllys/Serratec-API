package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.pedido.PedidoAtualizacaoDTO;
import org.serratec.dtos.pedido.PedidoCadastroDTO;
import org.serratec.dtos.pedido.PedidoCompletoDTO;
import org.serratec.entities.Pedido;
import org.serratec.entities.enums.StatusPedido;
import org.serratec.entities.exceptions.PedidoException;
import org.serratec.repositories.ClientRepository;
import org.serratec.repositories.PedidoRepository;
import org.serratec.repositories.ProdutoRepository;
import org.serratec.services.EmailService;
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
	
	@Autowired
    EmailService emailService;

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
			if(pedido.getClient().isEnabled()) {
				pedidoRepository.save(pedido);
				return new ResponseEntity<>("Pedido efetuado! Protocolo: " + pedido.getNumeroPedido(), HttpStatus.OK);
			}
			return new ResponseEntity<>("Cliente n??o encontrado", HttpStatus.BAD_REQUEST);
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@PutMapping("pedido/atualizar/{numeroPedido}")
	public ResponseEntity<?> putPedido(@PathVariable String numeroPedido, @RequestBody PedidoAtualizacaoDTO atualizado){
		
		Optional <Pedido> opcional = pedidoRepository.findByNumeroPedido(numeroPedido);
		
		if(opcional.isEmpty())
			return new ResponseEntity<>("Pedido n??o encontrado.", HttpStatus.BAD_REQUEST);
		
		Pedido pedido = opcional.get();
		Pedido pedidoAtualizado = null;
		
		if(pedido.getStatus().equals(StatusPedido.EM_ABERTO)) {
			pedidoAtualizado = atualizado.toPedido(produtoRepository, pedido);
			
			try {
				
				if(pedidoAtualizado.getProdutos() != null) {
					pedido.setProdutos(pedidoAtualizado.getProdutos());
				}
				if(pedidoAtualizado.getFormaDePagamento() != null) {
					pedido.setFormaDePagamento(pedidoAtualizado.getFormaDePagamento());
				}
				if(pedidoAtualizado.getStatus() != null) {
					pedido.setStatus(pedidoAtualizado.getStatus());
				} else {
					pedido.setStatus(StatusPedido.EM_ABERTO);
				}
				pedidoRepository.save(pedido);
				
				if(pedido.getStatus().equals(StatusPedido.FINALIZADO)) {
					pedido.getProdutos().forEach(p -> {
						if(p.getQuantidade() < p.getProduto().getQuantidadeEstoque()) {
							p.getProduto().subtrairQuantidadeEmEstoque(p.getQuantidade());
						} else {
							pedido.setStatus(StatusPedido.EM_ABERTO);
							pedidoRepository.save(pedido);
							throw new PedidoException("Pedido n??o pode ser realizado pois n??o h?? estoque suficiente");
						}
						
					});
					pedidoRepository.save(pedido);
					
					emailService.enviar("Pedido Finalizado!", "Seu pedido " + pedido.getNumeroPedido() +
							" foi finalizado com sucesso.Os produtos comprados foram: " 
							+ pedido.getProdutos().toString()
							+ " O valor final do pedido foi R$ " + pedido.getValorTotal() +
							" O prazo de entrega ?? de at?? 10 dias ??teis.",
							pedido.getClient().getEmail());
				} 
			}catch (DataIntegrityViolationException e) {
				return new ResponseEntity<>("Pedido n??o pode ser atualizado.", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("Este pedido est?? " + pedido.getStatus() + " e n??o pode ser atualizado.", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new PedidoCompletoDTO(pedido), HttpStatus.OK);
	}
	
}