package br.com.atlas.strategy;

import java.math.BigDecimal;

// Estratégia concreta: taxa fixa mais alta, cobrando mais por quilo e por quilômetro.
public class FreteSedex implements EstrategiaFrete {

    @Override
    public String nome() {
        return "Sedex";
    }

    @Override
    public BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm) {
        return new BigDecimal("15.00")
                .add(pesoEmKg.multiply(new BigDecimal("2.50")))
                .add(distanciaEmKm.multiply(new BigDecimal("0.10")));
    }
}