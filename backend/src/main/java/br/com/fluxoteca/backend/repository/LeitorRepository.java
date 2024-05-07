package br.com.fluxoteca.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fluxoteca.backend.model.Leitor;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {
    
}