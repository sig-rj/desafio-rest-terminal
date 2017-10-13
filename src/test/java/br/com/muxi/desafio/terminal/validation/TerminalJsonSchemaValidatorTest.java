package br.com.muxi.desafio.terminal.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.muxi.desafio.terminal.domain.Terminal;

public class TerminalJsonSchemaValidatorTest {

	@Test
	public void validate() {
		Terminal terminal = simpleTerminal();
		
		TerminalJsonSchemaValidator validator = new TerminalJsonSchemaValidator();
		validator.initialize(null);
		
		assertTrue("a validação não validou o terminal de teste", validator.isValid(terminal, null));
	}
	
	@Test
	public void withInvalidSam() {
		Terminal terminal = badSamTerminal();
		
		TerminalJsonSchemaValidator validator = new TerminalJsonSchemaValidator();
		validator.initialize(null);
		
		assertFalse("a validação validou o terminal com sam invalido", validator.isValid(terminal, null));
	}
	
	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
	
	private static Terminal badSamTerminal(){
		return new Terminal(44332211,"123","PWWIN",-10,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
}
