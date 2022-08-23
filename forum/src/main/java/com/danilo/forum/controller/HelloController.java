package com.danilo.forum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	//@RequestBody   como já colocamos o retcontroller acima da classe, não precisamos add o requestbody
	// se não colocarmos o spring vai entender que o "Hello World" é uma página
	// com essa anotação estamos informando que o retorno vem no corpo da página
	public String hello() {
		return "Hello World";
	}

}
