package br.com.atlas.templatemethod;

import br.com.atlas.Moeda;

import java.math.BigDecimal;

// Subclasse concreta: formata em colunas e sobrescreve o gancho rodape para mostrar o total.
public class RelatorioTexto extends GeradorRelatorio {

    private static final String SEPARADOR = "-".repeat(41) + "\n";

    @Override
    protected String cabecalho() {
        return "%-20s %5s %14s\n".formatted("Produto", "Qtd", "Total") + SEPARADOR;
    }

    @Override
    protected String linha(Venda venda) {
        return "%-20s %5d %14s\n".formatted(venda.produto(), venda.quantidade(), Moeda.formatar(venda.total()));
    }

    @Override
    protected String rodape(BigDecimal total) {
        return SEPARADOR + "%-26s %14s\n".formatted("Total", Moeda.formatar(total));
    }
}