package br.com.padroes.repository.service;

import br.com.padroes.repository.model.Produto;
import br.com.padroes.repository.dto.ProdutoDTO;
import br.com.padroes.repository.ProdutoRepository;
import br.com.padroes.repository.mapper.ProdutoMapper;
import br.com.padroes.repository.dto.SalvarProdutoDTO;
import br.com.padroes.repository.exceptions.ProdutoJaCadastradoException;
import br.com.padroes.repository.exceptions.ProdutoNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public class ProdutoService {

    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoMapper produtoMapper, ProdutoRepository produtoRepository) {
        this.produtoMapper = produtoMapper;
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDTO salvar(SalvarProdutoDTO salvarProdutoDTO) {
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