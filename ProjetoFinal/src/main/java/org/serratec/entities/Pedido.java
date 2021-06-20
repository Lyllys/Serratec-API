package org.serratec.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.serratec.entities.enums.Pagamento;
import org.serratec.entities.enums.StatusPedido;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String numeroPedido;
	
	@OneToMany (mappedBy = "pedido" , cascade = CascadeType.ALL)
	private List<PedidoProduto> produtos = new ArrayList<>();
	
	private double valorTotal;
	
	private LocalDateTime dataPedido;
	
	private StatusPedido status;
	
	private Pagamento formaDePagamento;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<PedidoProduto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<PedidoProduto> produtos) {
		this.produtos = produtos;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Pagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(Pagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public void calcularValorTotal() {
		double total = 0.00;
		for (PedidoProduto p : produtos) {
            total += p.getValorTotal();
        }

		this.valorTotal = total;
	}
	
}
