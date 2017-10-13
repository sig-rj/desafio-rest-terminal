package br.com.muxi.desafio.terminal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.beans.SamePropertyValuesAs.*;
import static org.hamcrest.CoreMatchers.equalTo;

import br.com.muxi.desafio.DesafioApplication;
import br.com.muxi.desafio.terminal.domain.Terminal;
import br.com.muxi.desafio.terminal.domain.TerminalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = DesafioApplication.class)
@AutoConfigureMockMvc
public class TerminalControllerTest {

	
	@Autowired
	private TerminalRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@After
	public void clean() {
		repository.deleteAll();
	}
	
	@Test
	public void addTerminal() throws Exception {
		mockMvc.perform(
					post("/1.0/terminal")
					.content("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN")
					.contentType( MediaType.TEXT_HTML)
				)
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", equalTo(44332211)))
		.andExpect(jsonPath("serial", equalTo("123")))
		.andExpect(jsonPath("model", equalTo("PWWIN")))
		.andExpect(jsonPath("sam", equalTo(0)))
		.andExpect(jsonPath("ptid", equalTo("F04A2E4088B")))
		.andExpect(jsonPath("plat", equalTo(4)))
		.andExpect(jsonPath("version", equalTo("8.00b3")))
		.andExpect(jsonPath("mxr", equalTo(0)))
		.andExpect(jsonPath("mxf", equalTo(16777216)))
		.andExpect(jsonPath("VERFM", equalTo("PWWIN")));
		
		Terminal fromDB = repository.findByLogic(44332211);
		
		assertNotNull("terminal não foi incluido no BD", fromDB);
		
		assertThat("propriedades do terminal adicionado ao BD não estão corretas", simpleTerminal(), samePropertyValuesAs( fromDB  ) );
	}
	
	@Test
	public void addTerminalinvalidInput() throws Exception {
		mockMvc.perform(
					post("/1.0/terminal")
					.content("123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN")
					.contentType( MediaType.TEXT_HTML)
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("message", equalTo("número de propriedades incorreto.") ) );
		
	}

	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
}
