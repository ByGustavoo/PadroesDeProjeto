package br.com.atlas.repository.service;

import br.com.atlas.repository.model.Produto;
import br.com.atlas.repository.dto.ProdutoDTO;
import br.com.atlas.repository.ProdutoRepository;
import br.com.atlas.repository.mapper.ProdutoMapper;
import br.com.atlas.repository.dto.SalvarProdutoDTO;
import br.com.atlas.repository.exceptions.ProdutoJaCadastradoException;
import br.com.atlas.repository.exceptions.ProdutoNaoEncontradoException;

import java.util.List;
import java.util.UUID;

// Service: concentra os casos de uso e recebe as dependências prontas.
public class ProdutoService {

    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    // Injeção de dependência pelo construtor: o service recebe o mapper e o repositório em vez de criá-los.
    public ProdutoService(ProdutoMapper produtoMapper, ProdutoRepository produtoRepository) {
        this.produtoMapper = produtoMapper;
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDTO salvar(SalvarProdutoDTO salvarProdutoDTO) {
        // Regra que depende de outros produtos fica no service, porque a entidade sozinha não a enxerga.
        if (produtoRepository.existePorNome(salvarProdutoDTO.nome())) {
            throw new ProdutoJaCadastradoException("Já existe um produto cadastrado com este nome!");
        }

        var produto = produtoRepository.salvar(produtoMapper.paraEntidade(salvarProdutoDTO));

        return produtoMapper.paraDTO(produto);
    }

    public List<ProdutoDTO> listar() {
        return produtoRepository.listar()
                .stream()
                .map(produtoMapper::paraDTO)
                .toList();
    }

    public ProdutoDTO buscar(UUID id) {
        return produtoMapper.paraDTO(buscarProduto(id));
    }

    public void deletar(UUID id) {
        produtoRepository.deletar(buscarProduto(id).getId());
    }

    private Produto buscarProduto(UUID id) {
        return produtoRepository.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado!"));
    }
}