package br.com.atlas.repository.dto;

import java.util.UUID;
import java.math.BigDecimal;

// DTO de saída: o que o service devolve. A entidade nunca sai da camada de serviço.
public record ProdutoDTO(UUID id, String nome, BigDecimal preco, boolean disponivel) {
}