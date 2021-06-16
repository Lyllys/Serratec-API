package org.serratec.dtos.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.serratec.entities.Client;
import org.serratec.entities.Pedido;
import org.serratec.entities.PedidoProduto;
import org.serratec.entities.exceptions.PedidoException;
import org.serratec.repositories.ClientRepository;
import org.serratec.repositories.ProdutoRepository;

public class PedidoCadastroDTO {

	private String numeroPedido = this.gerarNovoNumeroPedido();
	private String email;
	//private PedidoCadastroEnderecoDTO enderecoEntrega;
	//private Pagamento formaDePagamento
	private List<PedidoProdutoCadastroDTO> produtos  = new ArrayList<>();
	//private CupomDescontoCupom;
	
	public Pedido toPedido(ClientRepository clientRepository ,ProdutoRepository produtoRepository ) {
		
		Pedido pedido = new Pedido();
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setNumeroPedido(numeroPedido);
		
		Optional<Client> client = clientRepository.findByEmail(email);
		
		if(client.isEmpty()) {
			throw new PedidoException("Email não informado");
		}
		
		pedido.setClient(client.get());
	
		for (PedidoProdutoCadastroDTO p : produtos) {
			PedidoProduto pedidoProduto = p.toPedidoProduto(produtoRepository);
			pedidoProduto.setPedido(pedido);
			pedido.getProdutos().add(pedidoProduto);
		}
		
		
		return pedido;
		
	}
	
	private String gerarNovoNumeroPedido() {
		
		LocalDateTime agora = LocalDateTime.now();
		Random randomico = new Random();
		String codigo = "v";
		codigo += agora.getYear();
		codigo += agora.getMonth();
		codigo += agora.getDayOfMonth();
		codigo += agora.getHour();
		codigo += agora.getMinute();
		codigo += agora.getSecond();
		
		for(int i = 0; i < 10; i++) {
			codigo += randomico.nextInt(10);
		}
		
		return codigo;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public String getEmail() {
		return email;
	}

	public List<PedidoProdutoCadastroDTO> getProdutos() {
		return produtos;
	}
	
	
}
