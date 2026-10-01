package br.com.atlas;

import java.math.BigDecimal;
import java.util.Locale;

// Formata valores no padrão brasileiro (R$ 1.234,56).
public final class Moeda {

    private static final Locale BRASIL = Locale.of("pt", "BR");

    private Moeda() {
    }

    public static String formatar(BigDecimal valor) {
        return String.format(BRASIL, "R$ %,.2f", valor);
    }
}