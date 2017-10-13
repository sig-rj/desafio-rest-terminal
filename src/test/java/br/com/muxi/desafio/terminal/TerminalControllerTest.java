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

	
	private static final String JSON_TERMINAL = "{\"logic\": 44332211,\"serial\": \"1234\",\"model\": \"PWWIN\"" +
	",\"sam\": 1,\"ptid\": \"F04A2E4088B\",\"plat\": 4,\"version\": \"8.00b3\"," +
	"\"mxr\": 0,\"mxf\": 16777216,\"VERFM\":\"PWWIN\" }";
	
	private static final String JSON_TERMINAL_OTHER_LOGIC = "{\"logic\": 222223,\"serial\": \"1234\",\"model\": \"PWWIN\"" +
			",\"sam\": 1,\"ptid\": \"F04A2E4088B\",\"plat\": 4,\"version\": \"8.00b3\"," +
			"\"mxr\": 0,\"mxf\": 16777216,\"VERFM\":\"PWWIN\" }";
	
	private static final String JSON_TERMINAL_NO_LOGIC = "{\"serial\": \"1234\",\"model\": \"PWWIN\"" +
			",\"sam\": 1,\"ptid\": \"F04A2E4088B\",\"plat\": 4,\"version\": \"8.00b3\"," +
			"\"mxr\": 0,\"mxf\": 16777216,\"VERFM\":\"PWWIN\" }";
	
	private static final String BAD_JSON_TERMINAL = "{\"logic\": 44332211,\"serial\": \"1234\",\"model\": \"PWWIN\"" +
			",\"sam\": -1,\"ptid\": \"F04A2E4088B\",\"plat\": 4,\"version\": \"8.00b3\"," +
			"\"mxr\": 0,\"mxf\": 16777216,\"VERFM\":\"PWWIN\" }";

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
	
	@Test
	public void findTerminal() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(
					get("/1.0/terminal/44332211")
					.contentType( MediaType.APPLICATION_JSON)
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
		
	}
	
	@Test
	public void tryFindTerminalWithNotNumberLogic() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(
					get("/1.0/terminal/SA44332211")
					.contentType( MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("message", equalTo("logic não é um número inteiro: SA44332211") ) );
		
	}
	
	
	@Test
	public void tryFindTerminalNotFound() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(
					get("/1.0/terminal/222222")
					.contentType( MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("message", equalTo("Terminal não encontrado logic: 222222") ) );
		
	}
	
	@Test
	public void update() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(put("/1.0/terminal/44332211")
				.content(JSON_TERMINAL)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("logic", equalTo(44332211)))
			.andExpect(jsonPath("serial", equalTo("1234")))
			.andExpect(jsonPath("model", equalTo("PWWIN")))
			.andExpect(jsonPath("sam", equalTo(1)))
			.andExpect(jsonPath("ptid", equalTo("F04A2E4088B")))
			.andExpect(jsonPath("plat", equalTo(4)))
			.andExpect(jsonPath("version", equalTo("8.00b3")))
			.andExpect(jsonPath("mxr", equalTo(0)))
			.andExpect(jsonPath("mxf", equalTo(16777216)))
			.andExpect(jsonPath("VERFM", equalTo("PWWIN")));
		
		Terminal fromDB = repository.findByLogic(44332211);
		
		assertNotNull("terminal não foi encontrado após o update no BD", fromDB);
		
		assertThat("propriedades do terminal atualizado no BD não estão corretas", 
				new Terminal(44332211,"1234","PWWIN",1,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"), 
				samePropertyValuesAs( fromDB  ) );
	}
	
	@Test
	public void updateNoLogicInJson() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(put("/1.0/terminal/44332211")
				.content(JSON_TERMINAL_NO_LOGIC)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("logic", equalTo(44332211)))
			.andExpect(jsonPath("serial", equalTo("1234")))
			.andExpect(jsonPath("model", equalTo("PWWIN")))
			.andExpect(jsonPath("sam", equalTo(1)))
			.andExpect(jsonPath("ptid", equalTo("F04A2E4088B")))
			.andExpect(jsonPath("plat", equalTo(4)))
			.andExpect(jsonPath("version", equalTo("8.00b3")))
			.andExpect(jsonPath("mxr", equalTo(0)))
			.andExpect(jsonPath("mxf", equalTo(16777216)))
			.andExpect(jsonPath("VERFM", equalTo("PWWIN")));
		
		Terminal fromDB = repository.findByLogic(44332211);
		
		assertNotNull("terminal não foi encontrado após o update no BD", fromDB);
		
		assertThat("propriedades do terminal atualizado no BD não estão corretas", 
				new Terminal(44332211,"1234","PWWIN",1,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN"), 
				samePropertyValuesAs( fromDB  ) );
	}
	
	@Test
	public void updateLogicNotMatchJsonLogic() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(put("/1.0/terminal/44332211")
				.content(JSON_TERMINAL_OTHER_LOGIC)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("message", equalTo("Paramateros inválidos.") ) );
		
		
	}
	
	@Test
	public void updateBadJson() throws Exception {
		repository.save(simpleTerminal());
		repository.save(otherTerminal());
		
		mockMvc.perform(put("/1.0/terminal/44332211")
				.content(BAD_JSON_TERMINAL)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("message", equalTo("Terminal não é válido segundo o json schema.") ) );
		
		
	}
	
	
	@Test
	public void shouldNotAcceptDelete() throws Exception {
		mockMvc.perform(delete("/1.0/terminal/22222"))
		.andExpect(status().isMethodNotAllowed());
	}	
	
	@Test
	public void shouldNotAcceptPostJson() throws Exception {
		mockMvc
			.perform(post("/1.0/terminal")
			.content(JSON_TERMINAL)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnsupportedMediaType());
	}

	private static Terminal simpleTerminal(){
		return new Terminal(44332211,"123","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
	
	private static Terminal otherTerminal(){
		return new Terminal(77712233,"1234","PWWIN",0,"F04A2E4088B",4,"8.00b3",0,16777216,"PWWIN");
	}
}
