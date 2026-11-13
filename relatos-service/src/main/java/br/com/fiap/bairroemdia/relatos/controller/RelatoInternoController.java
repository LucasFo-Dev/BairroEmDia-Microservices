package br.com.fiap.bairroemdia.relatos.controller;

import br.com.fiap.bairroemdia.relatos.dto.*;
import br.com.fiap.bairroemdia.relatos.service.RelatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interno/relatos")
public class RelatoInternoController {
    private final RelatoService service;

    public RelatoInternoController(RelatoService service) {
        this.service = service;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RelatoResponse> alterarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return ResponseEntity.ok(service.alterarStatus(id, request.status()));
    }
}
