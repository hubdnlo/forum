package com.danilo.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.danilo.forum.modelo.Curso;
import com.danilo.forum.modelo.Topico;
import com.danilo.forum.repository.CursoRepository;

public class TopicoForm { // toda vez que uma possoa for criar um novo tópico, ela precisa enviar estes três dados/atributos
	//os demais atributos que não estão aqui já serão criados automaticamente. Ex: Data
	
	@NotNull @NotEmpty @Length(min = 5) // @ esses são validações do ben validation, 
	//ou seja se as informações vierem diferentes, será apresentado erro e não serão salvas no banco
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	@NotNull @NotEmpty
	private String nomeCurso;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Topico converter(CursoRepository cursoRepository) { // uso o CursoRepository p/ pegar o curso pelo nome
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso); // instancio um objeto do tipo Topico para ficar no padrão e eu conseguir salvar no banco
	}
	
	
	
}
