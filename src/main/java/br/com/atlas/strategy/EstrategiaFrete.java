package br.com.atlas.strategy;

import java.math.BigDecimal;

// Strategy: contrato comum a todos os algoritmos de frete. Cada forma de calcular vira uma classe que o implementa.
public interface EstrategiaFrete {

    String nome();

    // O ponto que varia entre as estratégias: quem chama não sabe qual cálculo vai rodar.
    BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm);
}