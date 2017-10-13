Build
=========

O build necessita de java 8 instalado

No diretório do projeto, executar o maven install

    ./mvnw install


Executar
========

## Executar direto com o maven

    ./mvnw spring-boot:run

## Executar o jar

Após o maven install, mudar para o diretório target


    cd target


e executar o jar


    java -jar .\desafio-terminal-0.1.0.jar
    
REST
=====

O serviço é acessado na porta 8080


## add Terminal

POST
header: Content-Type: text/html
body: 44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN
http://localhost:8080/1.0/terminal

## find Terminal
GET
http://localhost:8080/1.0/terminal/44332211

## update Terminal

PUT
header: Content-Type: application/json
body: 

{
    "logic": 44332211,
    "serial": "123",
    "model": "PWWIN",
    "sam": 0,
    "ptid": "F04A2E4088B",
    "plat": 4,
    "version": "8.00b3",
    "mxr": 0,
    "mxf": 16777216,
    "VERFM": "PWWIN"
}

http://localhost:8080/1.0/terminal/44332211

## JsonSchema

{
	"title": "Terminal",
	"type": "object",
	"properties": {
		"logic": {
		"type": "integer"
		},
		"serial": {
			"type": "string"
		},
		"model": {
			"type": "string"
		},
		"sam": {
			"type": "integer",
			"minimum": 0
		},
		"ptid": {
			"type": "string"
		},
		"plat": {
			"type": "integer"
		},
		"version": {
			"type": "string"
		},
		"mxr": {
			"type": "integer"
		},
		"VERFM": {
			"type": "string"
		}
	},
	"required": ["logic", "serial", "model", "version"]
}



