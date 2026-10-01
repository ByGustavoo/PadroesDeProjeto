package br.com.atlas.builder;

import java.math.BigDecimal;

// Parte do produto montado pelo Builder: um item do pedido, imutável por ser um record.
public record ItemPedido(String produto, int quantidade, BigDecimal precoUnitario) {

    // Construtor compacto do record: valida a quantidade antes de criar o item.
    public ItemPedido {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade do item deve ser maior que zero!");
        }
    }

    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}