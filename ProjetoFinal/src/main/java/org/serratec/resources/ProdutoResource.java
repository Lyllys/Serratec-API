package org.serratec.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.produto.ProdutoCadastroDTO;
import org.serratec.dtos.produto.ProdutoCompletoDTO;
import org.serratec.entities.Produto;
import org.serratec.repositories.CategoriaRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

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
		Produto produto = dto.toProduto(categoriaRepository);
		if (produto.getPreco() <= 0.00) {
			return new ResponseEntity<> ("Preço inválido", HttpStatus.NOT_ACCEPTABLE);
		}
		produtoRepository.save(produto);
		return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.OK);

	}
}
