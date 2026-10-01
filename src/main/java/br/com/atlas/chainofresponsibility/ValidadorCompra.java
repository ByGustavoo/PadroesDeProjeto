package br.com.atlas.chainofresponsibility;

import java.util.Optional;

// Handler base: cada validador é um elo da corrente e conhece apenas o próximo.
public abstract class ValidadorCompra {

    // Referência ao próximo elo; fica null no último.
    private ValidadorCompra proximo;

    // Liga os validadores na ordem recebida e devolve o primeiro, que é a entrada da cadeia.
    public static ValidadorCompra encadear(ValidadorCompra primeiro, ValidadorCompra... demais) {
        var atual = primeiro;

        for (var proximo : demais) {
            atual.proximo = proximo;
            atual = proximo;
        }

        return primeiro;
    }

    // Se este elo encontrar um problema, a cadeia para aqui; se não, a compra segue para o próximo.
    public final ResultadoValidacao validar(Compra compra) {
        return encontrarProblema(compra)
                .map(ResultadoValidacao::reprovar)
                .orElseGet(() -> proximo == null ? ResultadoValidacao.aprovar() : proximo.validar(compra));
    }

    // A única coisa que cada validador concreto implementa: a própria regra.
    protected abstract Optional<String> encontrarProblema(Compra compra);
}