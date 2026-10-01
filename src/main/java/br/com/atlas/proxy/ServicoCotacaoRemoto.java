package br.com.atlas.proxy;

import java.util.Map;
import java.util.Locale;
import java.time.Duration;
import java.math.BigDecimal;

// Objeto real: simula uma API externa lenta, que o Proxy vai proteger.
public class ServicoCotacaoRemoto implements ServicoCotacao {

    // Contador que mostra quantas vezes o serviço real foi de fato chamado.
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

    // Simula os 200 ms de uma chamada de rede.
    private void simularLatenciaDeRede() {
        try {
            Thread.sleep(Duration.ofMillis(200));
        } catch (InterruptedException excecao) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("A consulta de cotação foi interrompida!", excecao);
        }
    }
}