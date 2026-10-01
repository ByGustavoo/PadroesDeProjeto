package br.com.atlas.factory;

import br.com.atlas.Exemplo;

import java.util.Map;
import java.util.List;

// Cliente: pede notificações pelo tipo e usa todas pela mesma interface.
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
            // O cliente nunca escreve new NotificacaoEmail(): a Factory decide e devolve a interface.
            var notificacao = NotificacaoFactory.criar(envio.getKey());
            IO.println("  " + notificacao.enviar(envio.getValue(), "Seu pedido foi enviado!"));
        }
    }
}