package br.com.fiap.bairroemdia.atendimentos.client;

import br.com.fiap.bairroemdia.atendimentos.exception.*;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RelatosFallbackFactory implements FallbackFactory<RelatosClient> {
    @Override
    public RelatosClient create(Throwable causa) {
        return (id, request) -> {
            for (Throwable atual = causa; atual != null; atual = atual.getCause()) {
                if (atual instanceof FeignException.NotFound) {
                    throw new RecursoNaoEncontrado("Relato não encontrado: " + id);
                }
                if (atual instanceof FeignException.Conflict) {
                    throw new ConflitoDeEstado("O relato não permite a transição solicitada.");
                }
            }
            throw new RelatosIndisponiveis("Serviço de relatos indisponível. Tente novamente.");
        };
    }
}
