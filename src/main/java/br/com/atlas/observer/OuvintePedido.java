package br.com.atlas.observer;

// Observer: contrato de quem quer ser avisado. Por ter um só método, um ouvinte também pode ser uma lambda.
@FunctionalInterface
public interface OuvintePedido {

    void aoCriarPedido(PedidoCriado evento);
}