package br.com.padroes.chainofresponsibility;

import java.util.Optional;

public class ValidadorEstoque extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.quantidade() > compra.estoqueDisponivel()) {
            return Optional.of("Estoque insuficiente para a quantidade solicitada!");
        }

        return Optional.empty();
    }
}