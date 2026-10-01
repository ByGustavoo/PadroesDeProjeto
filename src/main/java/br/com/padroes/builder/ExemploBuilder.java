package br.com.padroes.builder;

import br.com.padroes.Moeda;
import br.com.padroes.Exemplo;

import java.math.BigDecimal;

public class ExemploBuilder implements Exemplo {

    @Override
    public String titulo() {
        return "Builder";
    }

    @Override
    public void executar() {
        var pedido = Pedido.builder()
                .cliente("Ana Souza")
                .enderecoEntrega("Rua das Flores, 120 - São Paulo/SP")
                .item("Teclado mecânico", 1, new BigDecimal("349.90"))
                .item("Mouse sem fio", 2, new BigDecimal("89.90"))
                .desconto(new BigDecimal("10"))
                .observacao("Entregar em horário comercial")
                .build();

        IO.println("Pedido de " + pedido.cliente() + " para " + pedido.enderecoEntrega());
        pedido.itens().forEach(item -> IO.println("  %dx %-18s %s".formatted(item.quantidade(), item.produto(), Moeda.formatar(item.subtotal()))));
        IO.println("  Subtotal:   " + Moeda.formatar(pedido.subtotal()));
        IO.println("  Desconto:   " + pedido.percentualDesconto() + "%");
        IO.println("  Total:      " + Moeda.formatar(pedido.total()));
        pedido.observacao().ifPresent(observacao -> IO.println("  Observação: " + observacao));

        try {
            Pedido.builder().cliente("Bruno Lima").build();
        } catch (IllegalStateException excecao) {
            IO.println("Pedido incompleto rejeitado no build(): " + excecao.getMessage());
        }
    }
}