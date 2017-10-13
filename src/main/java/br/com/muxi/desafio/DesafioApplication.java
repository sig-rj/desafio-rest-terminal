package br.com.muxi.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DesafioApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
}
