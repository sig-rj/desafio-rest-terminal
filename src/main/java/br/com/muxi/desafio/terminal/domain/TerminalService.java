package br.com.muxi.desafio.terminal.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.muxi.desafio.exception.BusinessAPIException;
import br.com.muxi.desafio.terminal.TerminalInputParser;
import br.com.muxi.desafio.terminal.exception.TerminalParseException;
import br.com.muxi.desafio.terminal.validation.TerminalJsonSchemaValidator;

@Service
public class TerminalService {

	@Autowired
	TerminalRepository repository;
	
	public Terminal addTerminal(String input) throws BusinessAPIException {
		try {
			return addTerminal(TerminalInputParser.build(input));
		} catch (TerminalParseException e) {
			throw new BusinessAPIException(e.getMessage());
		}
	}
	
	public Terminal addTerminal(Terminal terminal) throws BusinessAPIException {
		
		if( TerminalJsonSchemaValidator.isValidTerminal(terminal) ){				
			return repository.save(terminal);
		}else{
			throw new BusinessAPIException("Terminal não é válido segundo o json schema");
		}
		
	}

	public Terminal findByLogic(String logic) throws BusinessAPIException {
		Integer l = parseLogic(logic);
		
		return findByLogic(l);
	}

	private Integer parseLogic(String logic) throws BusinessAPIException {
		Integer l;
		
		try {
			l = Integer.parseInt(logic);	
		} catch (NumberFormatException e) {
			throw new BusinessAPIException("logic não é um número inteiro: " + logic);
		}
		return l;
	}
	
	public Terminal findByLogic(Integer logic) throws BusinessAPIException {
		Terminal terminal = repository.findByLogic(logic);
		
		if(terminal == null){
			throw new BusinessAPIException("Terminal não encontrado logic: " + logic);
		}
		
		return terminal;
	}

	public Terminal update(String logic, Terminal terminal) throws BusinessAPIException{
		Integer l = parseLogic(logic);
		
		if(terminal.getLogic() == null ){
			terminal.setLogic(l);
		}
		
		if( !terminal.getLogic().equals(l) ){
			throw new BusinessAPIException("Paramateros inválidos.");
		}
		
		return update(terminal);
	}
	
	public Terminal update(Terminal terminal) throws BusinessAPIException{
		
		if( terminal == null ){
			throw new BusinessAPIException("Terminal não pode ser null.");
		}
		
		if( terminal.getLogic() == null ){
			throw new BusinessAPIException("Terminal não tem logic.");
		}
		
		if( !TerminalJsonSchemaValidator.isValidTerminal(terminal) ){				
			throw new BusinessAPIException("Terminal não é válido segundo o json schema.");
		}
		
		if( !repository.exists(terminal.getLogic()) ){
			throw new BusinessAPIException("Terminal não encontrado : " + terminal.getLogic());
		}
		
		return repository.save(terminal);
	}
	
}
