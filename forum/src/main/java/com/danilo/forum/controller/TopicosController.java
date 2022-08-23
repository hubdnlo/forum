package com.danilo.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.danilo.forum.controller.dto.DetalhesDoTopicoDto;
import com.danilo.forum.controller.dto.TopicoDto;
import com.danilo.forum.controller.form.AtualizacaoTopicoForm;
import com.danilo.forum.controller.form.TopicoForm;
import com.danilo.forum.modelo.Topico;
import com.danilo.forum.repository.CursoRepository;
import com.danilo.forum.repository.TopicoRepository;


@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired // injetando o TopicoRepository
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	// PEGANDO AS INFORMAÇÕES NO BANCO DE DADOS
	public List<TopicoDto> lista(String nomeCurso) { // vou devolver uma lista com tópicos
		if (nomeCurso == null) {// significa que não está filtrando (está vindo tudo)
			 List<Topico> topicos = topicoRepository.findAll(); // obtendo toda a lista de tópicos, utilizando o Repository
				return TopicoDto.converter(topicos); // tenho uma lista de topicosDto e converto para uma lista de topicos
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			//com o findByCursoNome, o Spring gera a Query p/ fazer a consulta ao banco de dados
			//CursoNome é o nome do atributo da classe Topico, que acessa a classe Curso pegando o Nome
			// tbm é possível filtrar pelo nome do curso, caso ele venha no parâmetro da url (Ex: http://localhost:8080/topicos?nomeCurso=Spring Boot)
				return TopicoDto.converter(topicos);
		}
		
		//@ResponseBody = pegar o retorno desse método e devolver direto pro navegador
		
		// CRIAÇÃO DAS INFORMAÇÕES EM MEMÓRIA
		//Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Spring", "Programação"));
		// como ainda não estava utilizando o banco de dados (h2), os topicos eram criados em memória, ao instanciá-los.
		// chama o construtor dentro do Topico (dentro do packege Modelo)
		// chama o construtor dentro do Curso (dentro do packege Modelo)
		
//		//return Arrays.asList(topico, topico, topico);
		// para guardar esses topicos dentro da lista da linha 42 (3 tópicos iguais)
		
//		
//		//return TopicoDto.converter(Arrays.asList(topico, topico, topico));
//		

	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		//ResponseEntity, devolvo no corto da página o objeto que acabou de ser criado
		//RequestBody, significa que os dados virão preenchidos no corpo da página (não na url)
		// @Valid estamos informando ao Spring que ao receber as informações, ele deve rodar as validações do ben validation
		Topico topico = form.converter(cursoRepository); // recebo um TopicoForm e converto p/ um Topico p/ conseguir salvar no banco
		topicoRepository.save(topico); // salvando no banco
		
		// criando uma URI                                       pega o Id do tópico que acabou de ser salvo e converte p/ uma uri
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico)); // dentro do Topico temos dos as informações que o Dto precisa (retorno que vai no corpo)
		// ResponseEntity.created devolve a infomação de que deu td certo (o objeto foi criado no banco)
		// toda uri espera três informações: devolver o código 201 (created) p/ o cliente,
		// um cabeçalho hppt (chamado location) e, no corpo da resposta eu tenho que trazer uma representação deste recurso que acabei de criar
	} // TESTANDO NO POSTMAN:
	// Verbo Post, 
	// Body, row, monte a estrutura:
//	{
//	    "titulo":"Duvida Postman",
//	    "mensagem":"Texto da mensagem",
//	    "nomeCurso":"Spring Boot"
//	}
	// Headers, Content-Type, Value: application/json
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) { // @PathVariable estou informando que o id virá na url
		// por padrão o Spring associa os nomes do Path (id) versos o nome da url {id}
		// se, no Path, eu desse um nome diferente pro id, o Spring não conseguiria associar
		// então o código teria que ficar assim, exemplo: (@PathVariable("id") Long codigo)
		Optional<Topico> topico = topicoRepository.findById(id); // topicoRepository.findById(id), p/ buscar no banco de dados
		// depois que encontrou no banco, salvo na veriável chamada topico
		if (topico.isPresent()) { // se existe um id no banco, com o id informado na url
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get())); // quando damos um 'new' e converte p/ um DetalhesDoTopicoDto
		} // p/ pegar o topico que está dentro do optional
		
		return ResponseEntity.notFound().build(); // p/ retornar o 404 (não encontrado), caso o id não esteja presente no banco
	}
	
	@PutMapping("/{id}")
	@Transactional // p/ avisar pro Spring que é p/ realizar a atualização no banco, ao final desse método
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		// AtualizacaoTopicoForm, vão ter os dados que eu quero/posso fazer a atualização
		Optional<Topico> optional = topicoRepository.findById(id);
		// vai chegar o id do topico que eu quero atualizar (já buscando no banco de dados) e eu preciso subscrevê-lo, conforme a seguir:
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico)); // é o corpo que vai ser devolvido como resposta pelo servidor
		}
		
		return ResponseEntity.notFound().build();
	} // TESTANDO NO POSTMAN:
	// Verbo Put, 
	// Body, row, monte a estrutura:
//	{
//	    "titulo":"atualização xxx",
//	    "mensagem":"mensagem xxx"
//	}
	// Headers, Content-Type, Value: application/json
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
