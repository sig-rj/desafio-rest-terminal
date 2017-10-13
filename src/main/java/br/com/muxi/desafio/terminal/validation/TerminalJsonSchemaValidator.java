package br.com.muxi.desafio.terminal.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import br.com.muxi.desafio.terminal.domain.Terminal;


public class TerminalJsonSchemaValidator implements ConstraintValidator<TerminalJsonSchema,Terminal> {
	 public static final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
	 public static final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";
	 
	 
	 JsonSchema jsonSchemaNode;

	
	 
	public TerminalJsonSchemaValidator() {
		super();
		
		try {
			InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("terminal.json");
			InputStreamReader reader = new InputStreamReader(in);
			
			this.jsonSchemaNode = getSchemaNode( JsonLoader.fromReader(reader) );
		} catch (ProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(TerminalJsonSchema annotation) {
		
	}

	@Override
	public boolean isValid(Terminal t, ConstraintValidatorContext context) {
		
		return isValid(t);
	}
	
	public static boolean isValidTerminal(Terminal t){
		return new TerminalJsonSchemaValidator().isValid(t);
	}

	private boolean isValid(Terminal t){
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jsonNode = mapper.valueToTree(t);
		
		try {
			return isJsonValid(jsonNode);
		} catch (ProcessingException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isJsonValid( JsonNode jsonNode) throws ProcessingException, IOException
    {		    
        ProcessingReport report = this.jsonSchemaNode.validate(jsonNode);
        return report.isSuccess();
    } 
	
	private JsonSchema getSchemaNode(JsonNode jsonNode) throws ProcessingException
    {
        final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
        if (null == schemaIdentifier){
            ((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
        }
        
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        return factory.getJsonSchema(jsonNode);
    }
	
}
