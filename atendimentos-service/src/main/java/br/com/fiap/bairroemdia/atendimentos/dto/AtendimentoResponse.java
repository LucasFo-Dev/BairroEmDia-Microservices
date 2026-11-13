package br.com.fiap.bairroemdia.atendimentos.dto;

import br.com.fiap.bairroemdia.atendimentos.entity.Atendimento;
import br.com.fiap.bairroemdia.atendimentos.entity.Status;
import java.time.LocalDateTime;

public record AtendimentoResponse(Long id, Long relatoId, String equipe, String responsavel, String descricao, String prioridade, Status status, LocalDateTime criadoEm) {
    public static AtendimentoResponse de(Atendimento item) {
        return new AtendimentoResponse(item.getId(), item.getRelatoId(), item.getEquipe(), item.getResponsavel(), item.getDescricao(), item.getPrioridade(), item.getStatus(), item.getCriadoEm());
    }
}
