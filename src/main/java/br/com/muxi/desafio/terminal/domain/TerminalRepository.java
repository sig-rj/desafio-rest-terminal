package br.com.muxi.desafio.terminal.domain;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>{
	
	public Terminal findByLogic(Integer logic);
	
}
