package br.com.padroes.observer;

@FunctionalInterface
public interface OuvintePedido {

    void aoCriarPedido(PedidoCriado evento);
}