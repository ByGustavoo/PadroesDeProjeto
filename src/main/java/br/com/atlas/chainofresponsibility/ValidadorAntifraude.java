package br.com.atlas.chainofresponsibility;

import java.util.Optional;
import java.math.BigDecimal;

// Elo 4: compras de valor alto vão para análise manual.
public class ValidadorAntifraude extends ValidadorCompra {

    @Override
    protected Optional<String> encontrarProblema(Compra compra) {
        if (compra.valorTotal().compareTo(new BigDecimal("10000")) > 0) {
            return Optional.of("Compras acima de R$ 10.000,00 exigem análise manual!");
        }

        return Optional.empty();
    }
}