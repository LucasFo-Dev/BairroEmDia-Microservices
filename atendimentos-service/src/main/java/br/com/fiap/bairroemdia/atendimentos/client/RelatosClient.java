package br.com.fiap.bairroemdia.atendimentos.client;

import br.com.fiap.bairroemdia.atendimentos.dto.StatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "relatos-service", fallbackFactory = RelatosFallbackFactory.class)
public interface RelatosClient {
    @PutMapping("/api/v1/interno/relatos/{id}/status")
    void alterarStatus(@PathVariable("id") Long id, @RequestBody StatusRequest request);
}
