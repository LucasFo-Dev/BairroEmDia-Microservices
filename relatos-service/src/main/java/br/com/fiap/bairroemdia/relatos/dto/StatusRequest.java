package br.com.fiap.bairroemdia.relatos.dto;

import jakarta.validation.constraints.NotNull;
import br.com.fiap.bairroemdia.relatos.entity.Status;

public record StatusRequest(@NotNull Status status) {}
