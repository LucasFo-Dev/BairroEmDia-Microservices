package br.com.fiap.bairroemdia.atendimentos.service;

import br.com.fiap.bairroemdia.atendimentos.dto.*;
import br.com.fiap.bairroemdia.atendimentos.entity.*;
import br.com.fiap.bairroemdia.atendimentos.repository.AtendimentoRepository;
import br.com.fiap.bairroemdia.atendimentos.exception.*;
import br.com.fiap.bairroemdia.atendimentos.client.RelatosClient;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AtendimentoService {
    private final AtendimentoRepository repository;
    private final RelatosClient relatos;

    public AtendimentoService(AtendimentoRepository repository, RelatosClient relatos) {
        this.repository = repository;
        this.relatos = relatos;
    }

    public List<AtendimentoResponse> listar() {
        return repository.findAll(Sort.by("id")).stream().map(AtendimentoResponse::de).toList();
    }

    public AtendimentoResponse buscar(Long id) {
        return AtendimentoResponse.de(encontrar(id));
    }

    @Transactional
    public AtendimentoResponse criar(AtendimentoRequest r) {
        if (r.status() != Status.EM_TRIAGEM) throw new ConflitoDeEstado("Um atendimento deve começar em triagem.");
        var item = new Atendimento(r.relatoId(), r.equipe().strip(), r.responsavel().strip(), r.descricao().strip(), r.prioridade().strip(), Status.EM_TRIAGEM);
        repository.saveAndFlush(item);
        relatos.alterarStatus(r.relatoId(), new StatusRequest(item.getStatus().name()));
        return AtendimentoResponse.de(item);
    }

    @Transactional
    public AtendimentoResponse atualizar(Long id, AtendimentoRequest r) {
        var item = encontrar(id);
        if (!item.getRelatoId().equals(r.relatoId())) {
            throw new ConflitoDeEstado("O relato vinculado ao atendimento não pode ser alterado.");
        }
        validarTransicao(item.getStatus(), r.status());
        item.atualizar(r.relatoId(), r.equipe().strip(), r.responsavel().strip(), r.descricao().strip(), r.prioridade().strip(), r.status());
        repository.flush();
        relatos.alterarStatus(r.relatoId(), new StatusRequest(item.getStatus().name()));
        return AtendimentoResponse.de(item);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(encontrar(id));
    }

    private Atendimento encontrar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontrado("Atendimento não encontrado: " + id));
    }

    private void validarTransicao(Status atual, Status destino) {
        if (atual != destino && destino.ordinal() != atual.ordinal() + 1) {
            throw new ConflitoDeEstado("Transição inválida: " + atual + " → " + destino);
        }
    }
}
