package br.com.atlas.repository.dto;

import java.math.BigDecimal;

// DTO de entrada: só os dados que o cliente informa para cadastrar um produto.
public record SalvarProdutoDTO(String nome, BigDecimal preco, int estoque) {
}