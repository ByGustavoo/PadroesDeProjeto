package br.com.atlas.observer;

import java.util.List;
import java.util.ArrayList;

// Subject (publicador): mantém a lista de ouvintes e avisa todos, sem conhecer nenhum deles.
public class PublicadorPedidos {

    private final List<OuvintePedido> ouvintes = new ArrayList<>();

    // Ouvintes entram e saem em tempo de execução.
    public void inscrever(OuvintePedido ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void cancelarInscricao(OuvintePedido ouvinte) {
        ouvintes.remove(ouvinte);
    }

    // Percorre uma cópia da lista, para que um ouvinte possa se desinscrever durante o aviso.
    public void publicar(PedidoCriado evento) {
        List.copyOf(ouvintes).forEach(ouvinte -> ouvinte.aoCriarPedido(evento));
    }
}