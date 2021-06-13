package org.serratec.resources;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.dtos.produto.ProdutoCadastroDTO;
import org.serratec.dtos.produto.ProdutoCompletoDTO;
import org.serratec.entities.Produto;
import org.serratec.repositories.CategoriaRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<?> getProdutos(){
		List<Produto> todos = produtoRepository.findAll();
		List<ProdutoCompletoDTO> todosDTO = todos.stream().map(obj -> new ProdutoCompletoDTO(obj)).collect(Collectors.toList());
		
		return new ResponseEntity<> (todosDTO, HttpStatus.OK);
	}
	
	@PostMapping("/produtos/cadastrar")
	public ResponseEntity<?> postProduto(@Validated @RequestBody ProdutoCadastroDTO dto){
		Produto produto = dto.toProduto(categoriaRepository);
		produtoRepository.save(produto);
			return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.OK);
		
	}
}
