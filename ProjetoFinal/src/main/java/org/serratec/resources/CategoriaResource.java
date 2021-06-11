package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dtos.categoria.CategoriaCadastroDTO;
import org.serratec.dtos.categoria.CategoriaSimplificadoDTO;
import org.serratec.entities.Categoria;
import org.serratec.repositories.CategoriaRepository;
import org.serratec.repositories.ProdutoRepository;
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
public class CategoriaResource {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping("/categorias")
	public ResponseEntity<?> getCategorias(){
		List<Categoria> todas = categoriaRepository.findAll();
		List<CategoriaSimplificadoDTO> dtos = new ArrayList<>();
		
		for (Categoria c : todas) {
			
			dtos.add(new CategoriaSimplificadoDTO(c));
		}
		
		return new ResponseEntity<>(dtos , HttpStatus.OK);
	}
	
	@PostMapping("/categorias/cadastrar")
	public ResponseEntity<?> cadastrarCategorias(@Validated @RequestBody CategoriaCadastroDTO dto){
		
		try {
			Categoria categoria = dto.toCategoria(produtoRepository);
			categoriaRepository.save(categoria);
			return new ResponseEntity<> ("Categoria cadastrada com sucesso", HttpStatus.OK);
		}catch(DataIntegrityViolationException e) {
			return new ResponseEntity<>("Ops!Está categoria já existe!", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
}
