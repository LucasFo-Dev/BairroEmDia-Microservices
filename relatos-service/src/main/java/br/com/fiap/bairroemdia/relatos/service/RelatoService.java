package br.com.fiap.bairroemdia.relatos.service;

import br.com.fiap.bairroemdia.relatos.dto.*;
import br.com.fiap.bairroemdia.relatos.entity.*;
import br.com.fiap.bairroemdia.relatos.repository.RelatoRepository;
import br.com.fiap.bairroemdia.relatos.exception.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelatoService {
    private final RelatoRepository repository;

    public RelatoService(RelatoRepository repository) {
        this.repository = repository;
    }

    public List<RelatoResponse> listar() {
        return repository.findAll(Sort.by("id")).stream().map(RelatoResponse::de).toList();
    }

    public RelatoResponse buscar(Long id) {
        return RelatoResponse.de(encontrar(id));
    }

    @Transactional
    public RelatoResponse criar(RelatoRequest r) {
        var item = new Relato(r.titulo().strip(), r.descricao().strip(), r.categoria().strip(), r.logradouro().strip(), r.bairro().strip(), Status.RECEBIDA);
        repository.saveAndFlush(item);
        return RelatoResponse.de(item);
    }

    @Transactional
    public RelatoResponse atualizar(Long id, RelatoRequest r) {
        var item = encontrar(id);
        item.atualizar(r.titulo().strip(), r.descricao().strip(), r.categoria().strip(), r.logradouro().strip(), r.bairro().strip(), item.getStatus());
        return RelatoResponse.de(item);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(encontrar(id));
    }

    private Relato encontrar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontrado("Relato não encontrado: " + id));
    }

    private void validarTransicao(Status atual, Status destino) {
        if (atual != destino && destino.ordinal() != atual.ordinal() + 1) {
            throw new ConflitoDeEstado("Transição inválida: " + atual + " → " + destino);
        }
    }

    @Transactional
    public RelatoResponse alterarStatus(Long id, Status destino) {
        var item = encontrar(id);
        validarTransicao(item.getStatus(), destino);
        item.alterarStatus(destino);
        return RelatoResponse.de(item);
    }
}
