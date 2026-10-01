package br.com.atlas.repository;

import br.com.atlas.repository.model.Produto;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.LinkedHashMap;

// Implementação do Repository que guarda os produtos num Map. Trocá-la por JDBC não muda o service.
public class ProdutoRepositoryEmMemoria implements ProdutoRepository {

    private final Map<UUID, Produto> produtos = new LinkedHashMap<>();

    @Override
    public Produto salvar(Produto produto) {
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> listar() {
        return List.copyOf(produtos.values());
    }

    @Override
    public boolean existePorNome(String nome) {
        return nome != null && produtos.values()
                .stream()
                .anyMatch(produto -> produto.getNome().equalsIgnoreCase(nome.trim()));
    }

    @Override
    public void deletar(UUID id) {
        produtos.remove(id);
    }
}