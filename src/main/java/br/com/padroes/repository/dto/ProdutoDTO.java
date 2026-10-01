package br.com.padroes.repository.dto;

import java.util.UUID;
import java.math.BigDecimal;

public record ProdutoDTO(UUID id, String nome, BigDecimal preco, boolean disponivel) {
}