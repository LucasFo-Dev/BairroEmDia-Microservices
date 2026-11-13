package br.com.fiap.bairroemdia.atendimentos.dto;

import jakarta.validation.constraints.*;
import br.com.fiap.bairroemdia.atendimentos.entity.Status;

public record AtendimentoRequest(
    @NotNull @Positive Long relatoId,
    @NotBlank @Size(max = 100) String equipe,
    @NotBlank @Size(max = 100) String responsavel,
    @NotBlank @Size(max = 500) String descricao,
    @NotBlank @Size(max = 30) String prioridade,
    @NotNull Status status
) {}
