package br.com.atlas.chainofresponsibility;

import java.util.Optional;

// Elo 3: o valor não pode passar do limite de crédito do cliente.
public class ValidadorLimiteCredito extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.valorTotal().compareTo(compra.limiteCredito()) > 0) {
            return Optional.of("O valor da compra ultrapassa o limite de crédito do cliente!");
        }

        return Optional.empty();
    }
}