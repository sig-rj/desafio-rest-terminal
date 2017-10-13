package br.com.muxi.desafio.terminal.domain;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.muxi.desafio.exception.BusinessAPIException;

import static org.hamcrest.beans.SamePropertyValuesAs.*;

@RunWith(SpringRunner.class)
public class TerminalServiceTest {

	@TestConfiguration
    static class TerminalServiceContextConfiguration {
  
        @Bean
        public TerminalService employeeService() {
            return new TerminalService();
        }
    }
	
	@Autowired
	private TerminalService service;
	
	@MockBean
	private TerminalRepository repository;
	
	@Test
	public void whenAddWithStringReturnNewTerminal() throws BusinessAPIException {
		Mockito.when( repository.save( (Terminal)notNull() ) )
		.thenReturn(new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
		Terminal terminal = this.service.addTerminal("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
		
		assertNotNull("terminal retornou nulo", terminal);
		
		assertThat(terminal, samePropertyValuesAs(simpleTerminal()));
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void errorWhenInvalidInputString() throws BusinessAPIException {
		
		Terminal terminal = this.service.addTerminal("44332211;123PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void errorWhenSamLessThanZEROInString() throws BusinessAPIException {
		
		Terminal terminal = this.service.addTerminal("44332211;123;PWWIN;-1;F04A2E4088B;4;8.00b3;0;16777216;PWWIN");
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void errorWhenMissingLogic() throws BusinessAPIException {
		
		Terminal terminal = this.service.addTerminal(new Terminal(null,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void errorWhenMissingSerial() throws BusinessAPIException {
		
		Terminal terminal = this.service.addTerminal(new Terminal(5467,null,"PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void errorWhenMissingVersion() throws BusinessAPIException {
		
		Terminal terminal = this.service.addTerminal(new Terminal(5467,"123","PWWIN",0,"F04A2E4088B",4,null,0,16777216,"PWWIN"));
		
	}
	
	@Test
	public void whenSearchByLogic() throws BusinessAPIException {
		final int LOGIC_ID = 44332211;
		
		Mockito.when( repository.findByLogic( 44332211 ) )
		.thenReturn(new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
		Terminal terminal = this.service.findByLogic(44332211);
		
		assertNotNull("terminal retornou nulo", terminal);
		
		assertThat(terminal, samePropertyValuesAs(simpleTerminal()));
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void whenSearchByLogicNotFound() throws BusinessAPIException {
		final int LOGIC_ID = 44332211;
		
		Mockito.when( repository.findByLogic( LOGIC_ID ) )
		.thenReturn(null);
		
		Terminal terminal = this.service.findByLogic(LOGIC_ID);
	}
	
	@Test(expected=BusinessAPIException.class)
	public void whenSearchByLogicNotANumber() throws BusinessAPIException {
				
		Terminal terminal = this.service.findByLogic("12312A123_B");
	}
	
	@Test
	public void updateTerminal() throws BusinessAPIException {
		
		Mockito.when( repository.exists( 44332211 ) )
		.thenReturn(true);
		
		Mockito.when( repository.save( (Terminal)notNull() ) )
		.thenReturn(new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
		Terminal terminal = this.service.update(simpleTerminal());
		
		assertNotNull("terminal retornou nulo", terminal);
		
		assertThat(terminal, samePropertyValuesAs(simpleTerminal()));
		
	}
	
	@Test(expected=BusinessAPIException.class)
	public void updateNotExistTerminal() throws BusinessAPIException {		
		Mockito.when( repository.exists( 44332211 ) )
		.thenReturn(false);
		
		Terminal terminal = this.service.update(simpleTerminal());
		
	}
	
	@Test
	public void updateTerminalLogicNull() throws BusinessAPIException {	
		Terminal terminal = simpleTerminal();
		
		terminal.setLogic(null);
		
		try {
			
			Terminal terminalRet = this.service.update(terminal);
		} catch (BusinessAPIException e) {
			assertEquals("Excessão levantada não foi a correta", "Terminal não tem logic.", e.getMessage());
			return;
		}
		
		fail("BusinessException não levantada");
	}
	
	@Test
	public void updateTerminalNull() throws BusinessAPIException {	
		try {
			
			Terminal terminalRet = this.service.update(null);
		} catch (BusinessAPIException e) {
			assertEquals("Excessão levantada não foi a correta", "Terminal não pode ser null.", e.getMessage());
			return;
		}
		
		fail("BusinessException não levantada");
	}
	
	@Test
	public void updateTerminalInvalido() throws BusinessAPIException {	
		try {
			
			Terminal terminalRet = this.service.update(new Terminal(44332211,"123","PWWIN",-10,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		} catch (BusinessAPIException e) {
			assertEquals("Excessão levantada não foi a correta", "Terminal não é válido segundo o json schema.", e.getMessage());
			return;
		}
		
		fail("BusinessException não levantada");
	}
	
	@Test
	public void updateTerminalStringLogic() throws BusinessAPIException {
		
		Mockito.when( repository.exists( 44332211 ) )
		.thenReturn(true);
		
		Mockito.when( repository.save( (Terminal)notNull() ) )
		.thenReturn(new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"));
		
		Terminal terminal = this.service.update("44332211", simpleTerminal());
		
		assertNotNull("terminal retornou nulo", terminal);
		
		assertThat(terminal, samePropertyValuesAs(simpleTerminal()));
		
	}
	
	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
	
	private static Terminal otherTerminal(){
		return new Terminal(77712233,"1234","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}

}
