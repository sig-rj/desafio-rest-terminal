package br.com.muxi.desafio.terminal;

import org.springframework.util.StringUtils;

import br.com.muxi.desafio.terminal.domain.Terminal;
import br.com.muxi.desafio.terminal.exception.TerminalParseException;

public class TerminalInputParser {

	private static final int LOGIC = 0;
	private static final int SERIAL = 1;
	private static final int MODEL = 2;
	private static final int SAM = 3;
	private static final int PTID = 4;
	private static final int PLAT = 5;
	private static final int VERSION = 6;
	private static final int MRX = 7;
	private static final int MRF = 8;
	private static final int VERFM = 9;
	
	public static Terminal build(String input) throws TerminalParseException {
		
		if( StringUtils.isEmpty(input) )
			throw new TerminalParseException("string vazia");
		
		String tokens[] = input.split(";");
		
		if(tokens.length != 10)
			throw new TerminalParseException("número de propriedades incorreto.");
		
		return new Terminal(
				asInt(tokens[LOGIC]),
				tokens[SERIAL],
				tokens[MODEL],
				asInt(tokens[SAM]),
				tokens[PTID],
				asInt(tokens[PLAT]),
				tokens[VERSION],
				asInt(tokens[MRX]),
				asInt(tokens[MRF]),
				tokens[VERFM]
				);
	}
	
	private static Integer asInt(String s) throws TerminalParseException{
		try {
			return Integer.parseInt(s);			
		} catch (Exception e) {
			throw new TerminalParseException("numero inválido.");
		}
	}
	
}
