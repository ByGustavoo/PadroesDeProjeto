package br.com.atlas.strategy;

import java.math.BigDecimal;

// Estratégia concreta: mais barata que o Sedex, com taxas menores por quilo e por quilômetro.
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