package br.com.fiap.bairroemdia.atendimentos.repository;

import br.com.fiap.bairroemdia.atendimentos.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {}
