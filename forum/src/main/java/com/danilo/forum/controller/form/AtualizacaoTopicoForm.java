package com.danilo.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.danilo.forum.modelo.Topico;
import com.danilo.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
	
	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
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
	
	// construtor/método p/ atualizar
	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getReferenceById(id); // atualizou em memória, qdo terminar ele atualiza no banco de dados
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		return topico; // retorno o tópico que foi atualizado
	}
	
	
}
