package br.com.muxi.desafio.terminal;

import static org.junit.Assert.*;

import static org.hamcrest.beans.SamePropertyValuesAs.*;
import org.junit.Test;

import br.com.muxi.desafio.terminal.exception.TerminalParseException;

public class TerminalInputParserTest {

	@Test
	public void shouldCreateTerminalFromString() throws TerminalParseException {
		Terminal terminal = TerminalInputParser.build("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
		
		assertNotNull("o parser retornou null", terminal);
		
		assertThat("Terminal retornado pelo parser não tem as propriedades corretas", 
				terminal, 
				samePropertyValuesAs(simpleTerminal() ));
		
	}
	
	@Test(expected=TerminalParseException.class)
	public void emptyString() throws TerminalParseException {
		TerminalInputParser.build("");
	}
	
	@Test(expected=TerminalParseException.class)
	public void missingOnePropertyString() throws TerminalParseException {
		TerminalInputParser.build("123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void morePropertiesString() throws TerminalParseException {
		TerminalInputParser.build("AMAIS;44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void shouldLogicBeNumber() throws TerminalParseException {
		TerminalInputParser.build("443322a11;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void shouldSamBeNumber() throws TerminalParseException {
		TerminalInputParser.build("44332211;123;PWWIN;0a;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void shouldPtidBeNumber() throws TerminalParseException {
		TerminalInputParser.build("44332211;123;PWWIN;0;F04A2E4088B;4a;8.00b3;0;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void shouldMxrBeNumber() throws TerminalParseException {
		TerminalInputParser.build("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0a;16777216;PWWIN");
	}
	
	@Test(expected=TerminalParseException.class)
	public void shouldMxfBeNumber() throws TerminalParseException {
		TerminalInputParser.build("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216s;PWWIN");
	}
	
	
	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
	
	private static String[] tokens(){
		return new String[] { "44332211","123","PWWIN","0","F04A2E4088B","4","8.00b3","0","16777216","PWWIN" };
	}

}
