package br.com.padroes.observer;

import java.util.List;
import java.util.ArrayList;

public class PublicadorPedidos {

    private final List<OuvintePedido> ouvintes = new ArrayList<>();

    public void inscrever(OuvintePedido ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void cancelarInscricao(OuvintePedido ouvinte) {
        ouvintes.remove(ouvinte);
    }

    public void publicar(PedidoCriado evento) {
        List.copyOf(ouvintes).forEach(ouvinte -> ouvinte.aoCriarPedido(evento));
    }
}