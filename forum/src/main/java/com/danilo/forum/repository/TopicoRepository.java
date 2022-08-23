package com.danilo.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danilo.forum.modelo.Topico;

// como é uma interface, não colocamos nenhuma anotação em cima dela
public interface TopicoRepository extends JpaRepository<Topico, Long> {
	// gerenerics, precisamos passar duas informações: a entidade (Topico) e, o outro, qual o tipo do Id da chave primária (Long)
	// em JpaRepository já temos todos os métodos p/ salvar, editar e excluir
	List<Topico> findByCursoNome(String nomeCurso);
	
	//caso você não desejasse seguir a convenção acima, teria que montar a query de consulta, conforme a seguir:
	//@Query(SELECT t FROM Topico t Where t.curso.nome = :nomeCurso")
	//List<Topico> carregarPeloNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
