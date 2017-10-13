package br.com.muxi.desafio.terminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.muxi.desafio.exception.BusinessAPIException;
import br.com.muxi.desafio.terminal.domain.Terminal;
import br.com.muxi.desafio.terminal.domain.TerminalService;

@RestController
@RequestMapping("/1.0/terminal")
public class TerminalController {
	
	@Autowired
	TerminalService service;
	
	@RequestMapping(value = {""},method = RequestMethod.POST, consumes = { MediaType.TEXT_HTML_VALUE })
	public Terminal insert(@RequestBody String inputValues) throws BusinessAPIException {
		
		return service.addTerminal(inputValues);
	}
	
	@RequestMapping(value = {"/{logic}"},method = RequestMethod.GET)
	public Terminal findByLogic(@PathVariable String logic) throws BusinessAPIException {
		return service.findByLogic(logic);
	}
	
	@RequestMapping(value = {"/{logic}"},method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Terminal update(@PathVariable String logic, @RequestBody Terminal terminal) throws BusinessAPIException {
		return service.update(logic, terminal);
	}
	
}
