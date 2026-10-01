package br.com.padroes.templatemethod;

import java.math.BigDecimal;

public record Venda(String produto, int quantidade, BigDecimal valorUnitario) {

    public BigDecimal total() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}