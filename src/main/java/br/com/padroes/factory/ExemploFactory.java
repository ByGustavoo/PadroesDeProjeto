package br.com.padroes.factory;

import br.com.padroes.Exemplo;

import java.util.Map;
import java.util.List;

public class ExemploFactory implements Exemplo {

    @Override
    public String titulo() {
        return "Factory";
    }

    @Override
    public void executar() {
        var envios = List.of(
                Map.entry(TipoNotificacao.EMAIL, "ana.souza@email.com"),
                Map.entry(TipoNotificacao.SMS, "+5511987654321"),
                Map.entry(TipoNotificacao.PUSH, "dispositivo-7f3a"));

        IO.println("O cliente pede um tipo e recebe a implementação certa, sem usar new nem conhecer as classes concretas:");

        for (var envio : envios) {
            var notificacao = NotificacaoFactory.criar(envio.getKey());
            IO.println("  " + notificacao.enviar(envio.getValue(), "Seu pedido foi enviado!"));
        }
    }
}