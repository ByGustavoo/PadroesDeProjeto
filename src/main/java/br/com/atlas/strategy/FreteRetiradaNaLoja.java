package br.com.atlas.strategy;

import java.math.BigDecimal;

// Estratégia concreta: o cliente busca na loja, então o frete é sempre zero.
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