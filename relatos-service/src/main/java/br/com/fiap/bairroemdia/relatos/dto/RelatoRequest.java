package br.com.fiap.bairroemdia.relatos.dto;

import jakarta.validation.constraints.*;

public record RelatoRequest(
    @NotBlank @Size(max = 80) String titulo,
    @NotBlank @Size(max = 500) String descricao,
    @NotBlank @Size(max = 40) String categoria,
    @NotBlank @Size(max = 120) String logradouro,
    @NotBlank @Size(max = 80) String bairro
) {}
