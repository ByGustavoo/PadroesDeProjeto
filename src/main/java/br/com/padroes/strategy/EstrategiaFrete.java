package br.com.padroes.strategy;

import java.math.BigDecimal;

public interface EstrategiaFrete {

    String nome();

    BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm);
}