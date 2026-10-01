package br.com.padroes.repository;

import br.com.padroes.repository.model.Produto;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ProdutoRepository {

    Produto salvar(Produto produto);

    Optional<Produto> buscarPorId(UUID id);

    List<Produto> listar();

    boolean existePorNome(String nome);

    void deletar(UUID id);
}