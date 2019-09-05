package curso.springboot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class TelefoneController {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@PostMapping("**/addFonePessoa/{pessoaid}")
	public ModelAndView addFonePessoa(@Valid Telefone telefone, BindingResult bind, @PathVariable("pessoaid") Long pessoaid) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaid);
		ModelAndView model = new ModelAndView("cadastro/perfil");
		
		if(bind.hasErrors()) {
			model.addObject("pessoaobj", pessoa.get());
			model.addObject("telefones", telefoneRepository.findByTelefonesPessoa(pessoaid));
			return model;
		}
		

		telefone.setPessoa(pessoa.get());
		telefoneRepository.save(telefone);
		model.addObject("pessoaobj", pessoa.get());
		model.addObject("telefone", new Telefone());
		model.addObject("telefones", telefoneRepository.findByTelefonesPessoa(pessoaid));
		
		return model;
		
	}
	
	@GetMapping("/removertelefone/{telefoneid}")
	public ModelAndView excluir(@PathVariable("telefoneid") Long telefoneid) {
		
		Pessoa pessoa = telefoneRepository.findById(telefoneid).get().getPessoa();

		telefoneRepository.deleteById(telefoneid);
		
		ModelAndView model = new ModelAndView("cadastro/perfil");	
		model.addObject("pessoaobj", pessoa);
		model.addObject("telefone", new Telefone());
		model.addObject("telefones", telefoneRepository.findByTelefonesPessoa(pessoa.getId()));
		
		return model;
	}
}
