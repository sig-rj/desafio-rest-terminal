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
	
}
