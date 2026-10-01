package br.com.atlas.proxy;

import br.com.atlas.Moeda;
import br.com.atlas.Exemplo;

import java.util.List;
import java.time.Duration;

// Cliente: usa a interface ServicoCotacao sem saber que está falando com um Proxy.
public class ExemploProxy implements Exemplo {

    @Override
    public String titulo() {
        return "Proxy";
    }

    @Override
    public void executar() {
        var servicoRemoto = new ServicoCotacaoRemoto();
        var moedas = List.of("USD", "EUR", "USD", "usd", "EUR");
        // Colocar o Proxy no lugar do serviço real é uma única linha: o resto do código não muda.
        ServicoCotacao servico = new ServicoCotacaoComCache(servicoRemoto);

        IO.println("O cliente usa a interface ServicoCotacao sem saber que existe um cache na frente:");

        for (var moeda : moedas) {
            var inicio = System.nanoTime();
            var cotacao = servico.cotar(moeda);
            var duracao = Duration.ofNanos(System.nanoTime() - inicio).toMillis();

            IO.println("  %s -> %s (%d ms)".formatted(moeda, Moeda.formatar(cotacao), duracao));
        }

        IO.println("Cotações pedidas: %d | consultas ao serviço remoto: %d".formatted(moedas.size(), servicoRemoto.consultasRealizadas()));
    }
}