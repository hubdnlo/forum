package com.danilo.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.danilo.forum.modelo.Topico;

public class TopicoDto {
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	// construtor, o Topico foi passado como parâmetro
	public TopicoDto(Topico topico) {// toda vez que eu der new, chamo um objeto do tipo Topico, então preecho os atributos
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<TopicoDto> converter(List<Topico> topicos) {		
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
		// conversão de uma lista de tópicos p/ uma lista de topicosDto
		// pedir pro Wagnão me explicar +
	}
	
	

}
