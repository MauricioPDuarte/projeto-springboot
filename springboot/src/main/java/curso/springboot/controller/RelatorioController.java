package curso.springboot.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.util.RelatorioUtil;
import net.sf.jasperreports.engine.JRException;

@Controller
public class RelatorioController {
	
	@Autowired
	private RelatorioUtil relatorioUtil;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping("/downloadRelatorio/{pessoaid}")
	public void downloadRelatorioPdfPorId(
			@PathVariable("pessoaid") Long pessoaid,
			HttpServletRequest request,
			HttpServletResponse response) throws JRException, SQLException, IOException {

		Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaid);
		Map<String, Object> param = new HashMap<>();
		param.put("ID_PESSOA", pessoaid);
		
		byte[] pdf = relatorioUtil.gerarRelatorioPdf("PessoaRelatorio", param, request.getServletContext());
		response.setContentLength(pdf.length);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=Perfil_"+ pessoa.get().getNome() +".pdf");
		response.getOutputStream().write(pdf);
		
	}
	
	
	
}
