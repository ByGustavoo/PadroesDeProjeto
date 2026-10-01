package br.com.atlas.observer;

import br.com.atlas.Moeda;

import java.util.List;
import java.util.ArrayList;

// Ouvinte concreto: monta o e-mail de confirmação do pedido.
public class OuvinteEnvioEmail implements OuvintePedido {

    private final List<String> emailsEnviados = new ArrayList<>();

    @Override
    public void aoCriarPedido(PedidoCriado evento) {
        emailsEnviados.add("Olá, %s! Seu pedido %s de %s foi confirmado.".formatted(evento.cliente(), evento.numero(), Moeda.formatar(evento.total())));
    }

    public List<String> emailsEnviados() {
        return List.copyOf(emailsEnviados);
    }
}