package org.generation.blogPessoal.repository;

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	/**
	 * Metodo utilizado para realizar pesquisa pela coluna descricao da tabela tema
	 * 
	 * @param tema
	 * @return Lista com descricao dos temas
	 * @author Wendell
	 * @since 1.0
	 */
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
}
