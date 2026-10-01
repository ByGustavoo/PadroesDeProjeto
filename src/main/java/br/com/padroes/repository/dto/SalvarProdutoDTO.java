package br.com.padroes.repository.dto;

import java.math.BigDecimal;

public record SalvarProdutoDTO(String nome, BigDecimal preco, int estoque) {
}