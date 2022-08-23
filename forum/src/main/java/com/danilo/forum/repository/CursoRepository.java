package com.danilo.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danilo.forum.modelo.Curso;


public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	//Query para ele consultar no banco, o nome do curso
	Curso findByNome(String nome);
	

}
