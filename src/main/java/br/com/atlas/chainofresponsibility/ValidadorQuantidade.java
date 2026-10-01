package br.com.atlas.chainofresponsibility;

import java.util.Optional;

// Elo 1: a compra precisa ter pelo menos um item.
public class ValidadorQuantidade extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.quantidade() <= 0) {
            return Optional.of("A quantidade deve ser maior que zero!");
        }

        return Optional.empty();
    }
}