package br.com.padroes.repository.mapper;

import br.com.padroes.repository.model.Produto;
import br.com.padroes.repository.dto.ProdutoDTO;
import br.com.padroes.repository.dto.SalvarProdutoDTO;

public class ProdutoMapper {

    public Produto paraEntidade(SalvarProdutoDTO salvarProdutoDTO) {
        return new Produto(salvarProdutoDTO.nome(), salvarProdutoDTO.preco(), salvarProdutoDTO.estoque());
    }

    public ProdutoDTO paraDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.isDisponivel());
    }
}