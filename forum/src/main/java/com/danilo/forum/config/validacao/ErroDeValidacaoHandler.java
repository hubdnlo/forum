package com.danilo.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // todo o erro que houver (exception) estamos informando ao Spring p/ passar 1o por aqui
// e ver se tem tratamento do erro (p/ ver se tem uma msg mais amigável p/ o usuário)
public class ErroDeValidacaoHandler { // handler é um interceptador de erros, se tiver alguma exception em qq método de qq 
	//controller, ele chama essa classe de validação de erros
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) // por mais que estejamos tratando o erro não é p/ que o erro pare de acontecer (apenas estamos tratando)
	@ExceptionHandler(MethodArgumentNotValidException.class) // para validação de erro de formulário
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) { // pegando os erros p/ tratar 
		//(lista de erros), então pego essa lista de erros e passo p/ o Dto p/ tratar
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); // LocaleContextHolder.getLocale p/ apresentar a msg de erro no idioma local do usuário
			
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
}