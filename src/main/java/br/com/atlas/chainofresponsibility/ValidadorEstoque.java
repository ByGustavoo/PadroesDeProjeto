package br.com.atlas.chainofresponsibility;

import java.util.Optional;

// Elo 2: a quantidade pedida precisa caber no estoque.
public class ValidadorEstoque extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.quantidade() > compra.estoqueDisponivel()) {
            return Optional.of("Estoque insuficiente para a quantidade solicitada!");
        }

        return Optional.empty();
    }
}