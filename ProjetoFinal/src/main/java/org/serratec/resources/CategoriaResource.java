package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.dtos.categoria.CategoriaCadastroDTO;
import org.serratec.dtos.categoria.CategoriaSimplificadoDTO;
import org.serratec.entities.Categoria;
import org.serratec.repositories.CategoriaRepository;
import org.serratec.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CategoriaResource {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping("/categorias")
	public ResponseEntity<?> getCategorias(@RequestParam(required = false) String nome) {

		if (nome == null) {
			List<Categoria> todas = categoriaRepository.findAll();
			List<CategoriaSimplificadoDTO> dtos = new ArrayList<>();

			for (Categoria c : todas) {
				if (c.getArquivado() == false) {
					dtos.add(new CategoriaSimplificadoDTO(c));
				}
			}
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} else {
			Optional<Categoria> todas = categoriaRepository.findByNomeIgnoreCase(nome);

			if (todas.isEmpty())
				return new ResponseEntity<>("Categoria não encontrada.", HttpStatus.OK);

			return new ResponseEntity<>(todas, HttpStatus.OK);
		}
	}
	

	@PostMapping("/categorias/cadastrar")
	public ResponseEntity<?> cadastrarCategorias(@Validated @RequestBody CategoriaCadastroDTO dto) {

		try {
			Categoria categoria = dto.toCategoria(produtoRepository);
			categoriaRepository.save(categoria);
			return new ResponseEntity<>("Categoria cadastrada com sucesso", HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Ops!Está categoria já existe!", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping("/categorias/alterar/{nome}")
	public ResponseEntity<?> putCategoria(@PathVariable String nome, @RequestBody Categoria modificado) {
		Optional<Categoria> opcional = categoriaRepository.findByNome(nome);

		if (opcional.isEmpty())
			return new ResponseEntity<>("Esta categoria não existe.", HttpStatus.NOT_FOUND);

		Categoria existente = opcional.get();
		existente.setNome(modificado.getNome());
		existente.setDescricao(modificado.getDescricao());
		existente.setProdutos(modificado.getProdutos());

		categoriaRepository.save(existente);

		return new ResponseEntity<>("Alterações realizadas com sucesso!", HttpStatus.OK);

	}

	@DeleteMapping("/categorias/deletar/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
		try {
			Optional<Categoria> opcional = categoriaRepository.findById(id);

			if (opcional.isEmpty()) {
				return new ResponseEntity<>("Categoria não encontrada.", HttpStatus.OK);
			} else {
				Categoria existente = opcional.get();
				if (existente.getProdutos().isEmpty()) {
					categoriaRepository.deleteById(id);
					return new ResponseEntity<>("Categoria deletada com sucesso!", HttpStatus.OK);
				} else {
					existente.setArquivado(true);
					categoriaRepository.save(existente);
					return new ResponseEntity<>("Categoria arquivada com sucesso!", HttpStatus.OK);
				}
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		}
	}
}
