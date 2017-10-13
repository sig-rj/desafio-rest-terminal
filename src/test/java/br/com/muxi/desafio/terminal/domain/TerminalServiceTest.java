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
	
	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}

}
