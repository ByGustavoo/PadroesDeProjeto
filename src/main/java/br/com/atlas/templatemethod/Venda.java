package br.com.atlas.templatemethod;

import java.math.BigDecimal;

// Dado de entrada dos relatórios.
public record Venda(String produto, int quantidade, BigDecimal valorUnitario) {

    public BigDecimal total() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}