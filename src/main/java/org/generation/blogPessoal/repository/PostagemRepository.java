package org.generation.blogPessoal.repository;

import java.util.List;
//import java.util.Optional;

import org.generation.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem,Long>{
	public List<Postagem> findAllByTituloContainingIgnoreCase (String titulo);
	
	
	//public Optional<Postagem> findByEmail(String email);
	
	//public List<Postagem> findAllByNomeContainingIgnoreCase(String nome);
	
	
	
}
