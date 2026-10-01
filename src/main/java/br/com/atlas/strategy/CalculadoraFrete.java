package br.com.atlas.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Contexto: usa uma estratégia sem conhecer a classe concreta dela.
public class CalculadoraFrete {

    // Depende só da interface, por isso aceita qualquer estratégia, inclusive as que ainda não existem.
    private EstrategiaFrete estrategia;

    public CalculadoraFrete(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    // A estratégia pode ser trocada em tempo de execução, sem if/else e sem mudar esta classe.
    public void trocarEstrategia(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    public String nomeDaEstrategia() {
        return estrategia.nome();
    }

    // Validação e arredondamento ficam no contexto; só o cálculo em si é delegado à estratégia.
    public BigDecimal calcular(BigDecimal pesoEmKg, BigDecimal distanciaEmKm) {
        if (pesoEmKg.signum() <= 0 || distanciaEmKm.signum() < 0) {
            throw new IllegalArgumentException("O peso deve ser maior que zero e a distância não pode ser negativa!");
        }

        return estrategia.calcular(pesoEmKg, distanciaEmKm).setScale(2, RoundingMode.HALF_UP);
    }
}