package br.com.padroes.chainofresponsibility;

import java.util.Optional;

public abstract class ValidadorCompra {

    private ValidadorCompra proximo;

    public static ValidadorCompra encadear(ValidadorCompra primeiro, ValidadorCompra... demais) {
        var atual = primeiro;

        for (var proximo : demais) {
            atual.proximo = proximo;
            atual = proximo;
        }

        return primeiro;
    }

    public final ResultadoValidacao validar(Compra compra) {
        return encontrarProblema(compra)
                .map(ResultadoValidacao::reprovar)
                .orElseGet(() -> proximo == null ? ResultadoValidacao.aprovar() : proximo.validar(compra));
    }

    protected abstract Optional<String> encontrarProblema(Compra compra);
}