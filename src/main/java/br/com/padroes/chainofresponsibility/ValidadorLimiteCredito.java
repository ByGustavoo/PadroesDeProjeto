package br.com.padroes.chainofresponsibility;

import java.util.Optional;

public class ValidadorLimiteCredito extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.valorTotal().compareTo(compra.limiteCredito()) > 0) {
            return Optional.of("O valor da compra ultrapassa o limite de crédito do cliente!");
        }

        return Optional.empty();
    }
}