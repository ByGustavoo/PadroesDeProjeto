package br.com.padroes.proxy;

import java.util.Map;
import java.util.Locale;
import java.time.Duration;
import java.math.BigDecimal;

public class ServicoCotacaoRemoto implements ServicoCotacao {

    private int consultasRealizadas;
    private final Map<String, BigDecimal> cotacoes = Map.of(
            "USD", new BigDecimal("5.42"),
            "EUR", new BigDecimal("5.91"),
            "GBP", new BigDecimal("6.97"));

    @Override
    public BigDecimal cotar(String moeda) {
        consultasRealizadas++;
        simularLatenciaDeRede();

        var cotacao = cotacoes.get(moeda.trim().toUpperCase(Locale.ROOT));

        if (cotacao == null) {
            throw new IllegalArgumentException("Moeda não suportada!");
        }

        return cotacao;
    }

    public int consultasRealizadas() {
        return consultasRealizadas;
    }

    private void simularLatenciaDeRede() {
        try {
            Thread.sleep(Duration.ofMillis(200));
        } catch (InterruptedException excecao) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("A consulta de cotação foi interrompida!", excecao);
        }
    }
}