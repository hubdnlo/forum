package com.danilo.forum.controller.dto;

import java.time.LocalDateTime;

import com.danilo.forum.modelo.Resposta;

public class RespostaDto {
	
	private Long id;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	
	// nesse construtor, estamos pegando as informações da classe Resposta (em package Modelo), 
	//e armazenamos nos atributos desta classe
	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao =  resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}
	
	
}
