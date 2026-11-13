package br.com.fiap.bairroemdia.relatos.repository;

import br.com.fiap.bairroemdia.relatos.entity.Relato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatoRepository extends JpaRepository<Relato, Long> {}
