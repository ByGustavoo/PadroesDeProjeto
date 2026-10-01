package br.com.atlas.observer;

import java.util.List;
import java.util.ArrayList;

// Ouvinte concreto: emite a nota fiscal do pedido.
public class OuvinteEmissaoNotaFiscal implements OuvintePedido {

    private final List<String> notasEmitidas = new ArrayList<>();

    @Override
    public void aoCriarPedido(PedidoCriado evento) {
        notasEmitidas.add("NF-" + evento.numero());
    }

    public List<String> notasEmitidas() {
        return List.copyOf(notasEmitidas);
    }
}