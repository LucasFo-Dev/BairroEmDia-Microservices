package br.com.fiap.bairroemdia.relatos.controller;

import br.com.fiap.bairroemdia.relatos.dto.*;
import br.com.fiap.bairroemdia.relatos.service.RelatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/relatos")
public class RelatoController {
    private final RelatoService service;

    public RelatoController(RelatoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RelatoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<RelatoResponse> criar(@Valid @RequestBody RelatoRequest request) {
        var resposta = service.criar(request);
        return ResponseEntity.created(URI.create("/api/v1/relatos/" + resposta.id())).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody RelatoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
