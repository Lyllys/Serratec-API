package org.serratec.dtos.produto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.serratec.entities.Categoria;
import org.serratec.entities.Produto;
import org.serratec.entities.exceptions.ProdutoException;
import org.serratec.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Value;

public class ProdutoCadastroDTO {

	@NotNull
	@NotBlank
	private String codigo;

	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	@NotNull
	private Double preco;
	
	@NotNull
	private Integer quantidadeEstoque;
	
	private LocalDateTime dataCadastro;
	
	@NotNull
	private String categoria;
	
	private String imagem;
	
	

	public Produto toProduto(CategoriaRepository categoriaRepository, String caminhoImagem) {
		
		System.out.println(caminhoImagem);
		
		Produto produto = new Produto();
		produto.setCodigo(this.codigo);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setPreco(this.preco);
		produto.setQuantidadeEstoque(this.quantidadeEstoque);
		produto.setDataCadastro(LocalDateTime.now());
		
		Optional<Categoria> categoria = categoriaRepository.findByNome(this.categoria);
		
		if(categoria.isEmpty()) {
			throw new ProdutoException("Categoria " + this.categoria + " não encontrada.");
		}
		
		produto.setCategoria(categoria.get());
		
		if(imagem != null) {
			byte[] img = Base64.decodeBase64(imagem);
			String nomeArquivo = "imagem_" + gerarCodigo() +".jpg";
			try {
				OutputStream out = new FileOutputStream(new File(caminhoImagem, nomeArquivo));
				out.write(img);
				out.close();
				produto.setImagem(nomeArquivo);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return produto;
		
	}
	
	public String gerarCodigo() {
			LocalDate agora = LocalDate.now();
			Random randomico = new Random();
			String codigo = "img";
			codigo += agora.getYear();
			codigo += agora.getMonth();
			codigo += agora.getDayOfMonth();
			
			for (int i = 0; i < 10; i++) {
				codigo += randomico.nextInt(10);
			}
		 return codigo;	
		}
	
	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getImagem() {
		return imagem;
	}

	public String getCodigo() {
		return codigo;
	}
	
}
