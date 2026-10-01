package br.com.padroes.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraFrete {

    private EstrategiaFrete estrategia;

    public CalculadoraFrete(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    public void trocarEstrategia(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    public String nomeDaEstrategia() {
        return estrategia.nome();
    }

    public BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm) {
        if (pesoEmKg.signum() <= 0 || distanciaEmKm.signum() < 0) {
            throw new IllegalArgumentException("O peso deve ser maior que zero e a distância não pode ser negativa!");
        }

        return estrategia.calcular(pesoEmKg, distanciaEmKm).setScale(2, RoundingMode.HALF_UP);
    }
}