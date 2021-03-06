package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
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
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/temas")
public class TemaController {

	@Autowired 
	private TemaRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Tema>> getAll(){
		List<Tema> temaLista = repository.findAll();

		if (temaLista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(repository.findAll());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescription(@PathVariable String descricao){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
		
	}
	
	@PostMapping("/save") 
	public ResponseEntity<Tema> post (@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(tema));
	}
	
	@PutMapping("/update") 
	public ResponseEntity<Tema> put (@RequestBody Tema tema){
		return ResponseEntity.ok(repository.save(tema));
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	
}
