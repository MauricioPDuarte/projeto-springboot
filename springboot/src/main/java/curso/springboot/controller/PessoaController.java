package curso.springboot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		model.addObject("pessoa", new Pessoa());
		model.addObject("pessoas", pessoaRepository.findAll());
		
		return model;
	}
	
	@PostMapping("**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		
		
		if(bindingResult.hasErrors()) {
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			model.addObject("pessoas", pessoasIt);
			return model;
		}
		
		pessoaRepository.save(pessoa);
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		model.addObject("pessoa", new Pessoa());
		model.addObject("pessoas", pessoasIt);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoa() {
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		model.addObject("pessoas", pessoasIt);
		model.addObject("pessoaobj", new Pessoa());
		return model;
	}
	
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		pessoa.ifPresent(p -> model.addObject("pessoa", p));
		return model;
	}
	
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
		pessoaRepository.deleteById(idpessoa);
		
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");	
		model.addObject("pessoas", pessoaRepository.findAll());
		model.addObject("pessoa", new Pessoa());
		
		return model;
	}
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView model = new ModelAndView("cadastro/cadastropessoa");
		model.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		model.addObject("pessoao", new Pessoa());
		return model;
	}
	
	@GetMapping("/perfilpessoa/{idpessoa}")
	public ModelAndView perfilpessoa(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView model = new ModelAndView("cadastro/perfil");
		pessoa.ifPresent(p -> {
			model.addObject("pessoaobj", p);
			model.addObject("telefone", new Telefone());
			model.addObject("telefones", telefoneRepository.findByTelefonesPessoa(idpessoa));
		});
		return model;
	}
}
