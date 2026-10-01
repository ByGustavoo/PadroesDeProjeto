package br.com.padroes.chainofresponsibility;

import java.util.Optional;
import java.math.BigDecimal;

public class ValidadorAntifraude extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.valorTotal().compareTo(new BigDecimal("10000")) > 0) {
            return Optional.of("Compras acima de R$ 10.000,00 exigem análise manual!");
        }

        return Optional.empty();
    }
}