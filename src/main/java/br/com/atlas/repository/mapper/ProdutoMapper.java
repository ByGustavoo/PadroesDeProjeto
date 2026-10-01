package br.com.atlas.repository.mapper;

import br.com.atlas.repository.model.Produto;
import br.com.atlas.repository.dto.ProdutoDTO;
import br.com.atlas.repository.dto.SalvarProdutoDTO;

// Mapper: converte entre DTO e entidade, sem tomar nenhuma decisão de negócio.
public class ProdutoMapper {

    public Produto paraEntidade(SalvarProdutoDTO salvarProdutoDTO) {
        return new Produto(salvarProdutoDTO.nome(), salvarProdutoDTO.preco(), salvarProdutoDTO.estoque());
    }

    public ProdutoDTO paraDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), produto.isDisponivel());
    }
}