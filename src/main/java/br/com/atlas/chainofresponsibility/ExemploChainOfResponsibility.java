package br.com.atlas.chainofresponsibility;

import br.com.atlas.Exemplo;

import java.util.List;
import java.math.BigDecimal;

// Cliente: monta a cadeia uma vez e envia cada compra ao primeiro elo.
public class ExemploChainOfResponsibility implements Exemplo {

    @Override
    public String titulo() {
        return "Chain of Responsibility";
    }

    @Override
    public void executar() {
        // Uma regra nova é só mais um elo nesta lista, sem mexer nos validadores existentes.
        var validador = ValidadorCompra.encadear(
                new ValidadorQuantidade(),
                new ValidadorEstoque(),
                new ValidadorLimiteCredito(),
                new ValidadorAntifraude());

        var compras = List.of(
                new Compra("Ana Souza", 2, new BigDecimal("699.80"), 15, new BigDecimal("5000")),
                new Compra("Bruno Lima", 20, new BigDecimal("1798.00"), 4, new BigDecimal("5000")),
                new Compra("Carla Dias", 1, new BigDecimal("1899.00"), 4, new BigDecimal("1000")),
                new Compra("Diego Rocha", 6, new BigDecimal("11394.00"), 10, new BigDecimal("20000")));

        IO.println("Cada compra passa por quantidade -> estoque -> limite de crédito -> antifraude, até o primeiro problema:");

        for (var compra : compras) {
            var resultado = validador.validar(compra);
            IO.println("  %-12s %-9s %s".formatted(compra.cliente(), resultado.aprovada() ? "APROVADA" : "REPROVADA", resultado.motivo()));
        }
    }
}