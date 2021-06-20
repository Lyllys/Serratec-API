package org.serratec.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.produto.ProdutoAtualizacaoDTO;
import org.serratec.dtos.produto.ProdutoCadastroDTO;
import org.serratec.dtos.produto.ProdutoCompletoDTO;
import org.serratec.entities.Pedido;
import org.serratec.entities.PedidoProduto;
import org.serratec.entities.Produto;
import org.serratec.repositories.CategoriaRepository;
import org.serratec.repositories.PedidoProdutoRepository;
import org.serratec.repositories.PedidoRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	PedidoProdutoRepository pedidoProdutoRepository;
	
	@Value("${serratec.upload_diretorio}")
	private String caminhoImagem;
	
	@GetMapping("/produtos")
	public ResponseEntity<?> getProdutos() {

			List<Produto> todos = produtoRepository.findAll();
			List<ProdutoCompletoDTO> dtos = new ArrayList<>();

			for (Produto p : todos) {
				if (p.getCategoria().getArquivado() == false) {
					dtos.add(new ProdutoCompletoDTO(p));
				}
			}
			return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/produtos/{nome}")
	public ResponseEntity<?> getProdutoPorNome(@PathVariable String nome){
		Optional<Produto> opcional = produtoRepository.findByNomeIgnoreCase(nome);
		
		if(opcional.isEmpty()) 
			return new ResponseEntity<> ("Produto inexistente." , HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<> (opcional, HttpStatus.OK);
		
	}
	
	@GetMapping("/produtos/filtro/{nome}")
	public ResponseEntity<?> getProdutoPorFiltroNome(@PathVariable String nome){
		List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
		
		if(produtos.isEmpty())
			return new ResponseEntity<> ("Não há produtos cadastrados com o nome informado." , HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<> (produtos, HttpStatus.OK);
		
	}
	
	@GetMapping("/produtos/arquivados")
	public ResponseEntity<?> getProdutosArquivados() {
		List<Produto> todos = produtoRepository.findAll();
		List<ProdutoCompletoDTO> dtos = new ArrayList<>();

		for (Produto p : todos) {
			if (p.getCategoria().getArquivado() == true) {
				dtos.add(new ProdutoCompletoDTO(p));
			}
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/produtos/imagem/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getCapa (@PathVariable Long id) throws IOException{
		Produto produto = produtoRepository.findById(id).get();
		
		byte[] imagem = Files.readAllBytes(Paths.get(produto.getImagem()));
				
		return new ResponseEntity<>(imagem , HttpStatus.OK); 
	}

	@PostMapping("/produtos/cadastrar")
	public ResponseEntity<?> postProduto(@Validated @RequestBody ProdutoCadastroDTO dto) {
		Produto produto = dto.toProduto(categoriaRepository, caminhoImagem);
		if (produto.getPreco() <= 0.00) {
			return new ResponseEntity<> ("Preço inválido", HttpStatus.NOT_ACCEPTABLE);
		}
		try {
			produtoRepository.save(produto);
			
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Não foi possível cadastrar o produto.", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.OK);

	}
	
	@PutMapping("/produtos/alterar/{codigo}")
	public ResponseEntity<?> putProdutos(@PathVariable String codigo, @RequestBody ProdutoAtualizacaoDTO modificado){
		
		Optional<Produto> opcional = produtoRepository.findByCodigo(codigo);
		
		if(opcional.isEmpty())
			return new ResponseEntity<> ("Produto não encontrado", HttpStatus.NOT_FOUND);
		
		Produto existente = opcional.get();
		Produto atualizado = modificado.toProduto(categoriaRepository, caminhoImagem);
		
		existente.setNome(atualizado.getNome());
		existente.setDescricao(atualizado.getDescricao());
		existente.setPreco(atualizado.getPreco());
		existente.setQuantidadeEstoque(atualizado.getQuantidadeEstoque());
		existente.setCategoria(atualizado.getCategoria());
		existente.setImagem(atualizado.getImagem());
		
		
		produtoRepository.save(existente);
		
		return new ResponseEntity<> ("Alterações realizadas com sucesso!" , HttpStatus.OK);
	}
	
	@DeleteMapping("/produtos/deletar/{id}")
	public ResponseEntity<?> deleteProdutos(@PathVariable Long id){
		
		Optional<Produto> opcional = produtoRepository.findById(id);
		
		if(opcional.isEmpty())
			return new ResponseEntity<> ("Produto não encontrado", HttpStatus.NOT_FOUND);
		
		Produto existente = opcional.get();
		
		Optional<PedidoProduto> pedidoProduto = pedidoProdutoRepository.findByProduto(existente);
		
		if(pedidoProduto.isEmpty()) {
			produtoRepository.deleteById(id);
			return new ResponseEntity<> ("Produto deletado com sucesso", HttpStatus.OK);
		}
		existente.setArquivado(true);
		produtoRepository.save(existente);
		return new ResponseEntity<> ("Produto arquivado com sucesso", HttpStatus.OK);
	}
	
}
