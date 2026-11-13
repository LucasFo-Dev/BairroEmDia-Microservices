package br.com.fiap.bairroemdia.atendimentos.controller;

import br.com.fiap.bairroemdia.atendimentos.dto.*;
import br.com.fiap.bairroemdia.atendimentos.service.AtendimentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class AtendimentoController {
    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoRequest request) {
        var resposta = service.criar(request);
        return ResponseEntity.created(URI.create("/api/v1/atendimentos/" + resposta.id())).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtendimentoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
