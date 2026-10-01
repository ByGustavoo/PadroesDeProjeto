package br.com.padroes.strategy;

import java.math.BigDecimal;

public class FretePac implements EstrategiaFrete {

    @Override
    public String nome() {
        return "PAC";
    }

    @Override
    public BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm) {
        return new BigDecimal("8.00")
                .add(pesoEmKg.multiply(new BigDecimal("1.20")))
                .add(distanciaEmKm.multiply(new BigDecimal("0.05")));
    }
}