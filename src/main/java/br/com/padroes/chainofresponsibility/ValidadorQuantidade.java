package br.com.padroes.chainofresponsibility;

import java.util.Optional;

public class ValidadorQuantidade extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.quantidade() <= 0) {
            return Optional.of("A quantidade deve ser maior que zero!");
        }

        return Optional.empty();
    }
}