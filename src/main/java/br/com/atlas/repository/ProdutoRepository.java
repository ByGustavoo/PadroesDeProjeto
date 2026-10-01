package br.com.atlas.repository;

import br.com.atlas.repository.model.Produto;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

// Repository: abstrai o acesso aos dados. Quem usa não sabe se eles estão em memória, em banco ou em arquivo.
public interface ProdutoRepository {

    Produto salvar(Produto produto);

    Optional<Produto> buscarPorId(UUID id);

    List<Produto> listar();

    boolean existePorNome(String nome);

    void deletar(UUID id);
}