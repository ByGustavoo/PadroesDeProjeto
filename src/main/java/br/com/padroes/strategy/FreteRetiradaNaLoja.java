package br.com.padroes.strategy;

import java.math.BigDecimal;

public class FreteRetiradaNaLoja implements EstrategiaFrete {

    @Override
    public String nome() {
        return "Retirada na loja";
    }

    @Override
    public BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm) {
        return BigDecimal.ZERO;
    }
}