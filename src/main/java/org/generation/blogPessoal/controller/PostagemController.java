package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<Postagem>> getAll() {
		List<Postagem> objetoLista = repository.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(repository.findAll());
		}
	}

	/*
	 * >>BOAZ CODE GET MAPPING ALL<<
	 * 
	 * @GetMapping("/todes") public ResponseEntity<List<Usuario>> pegarTodes(){
	 * List<Usuario> objetoLista = repositorio.findAll();
	 * 
	 * if (objetoLista.isEmpty()) { return ResponseEntity.status(204).build(); //204
	 * > no content } else { return ResponseEntity.status(200).body(objetoLista);
	 * //200 > ok } }
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/*
	 * >>BOAZ GET MAPPING ID<<
	 @GetMapping("/{id_usuario}")
	public ResponseEntity<Usuario> pegarPorId(@PathVariable(value = "id_usuario") Long idUsuario ){
		Optional<Usuario> objetoOptional = repositorio.findById(idUsuario);
		
		if (objetoOptional.isPresent()) {
			return ResponseEntity.status(200).body(objetoOptional.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}*/

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping("/save")
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(postagem));
	}

	@PutMapping("/update")
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem) {
		return ResponseEntity.ok(repository.save(postagem));
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

}
