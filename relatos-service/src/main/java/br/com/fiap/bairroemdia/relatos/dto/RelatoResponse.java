package br.com.fiap.bairroemdia.relatos.dto;

import br.com.fiap.bairroemdia.relatos.entity.Relato;
import br.com.fiap.bairroemdia.relatos.entity.Status;
import java.time.LocalDateTime;

public record RelatoResponse(Long id, String titulo, String descricao, String categoria, String logradouro, String bairro, Status status, LocalDateTime criadoEm) {
    public static RelatoResponse de(Relato item) {
        return new RelatoResponse(item.getId(), item.getTitulo(), item.getDescricao(), item.getCategoria(), item.getLogradouro(), item.getBairro(), item.getStatus(), item.getCriadoEm());
    }
}
