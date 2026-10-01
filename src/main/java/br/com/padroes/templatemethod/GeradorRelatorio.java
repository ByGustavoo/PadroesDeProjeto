package br.com.padroes.templatemethod;

import java.util.List;
import java.math.BigDecimal;

public abstract class GeradorRelatorio {

    public final String gerar(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            throw new IllegalArgumentException("Não há vendas para gerar o relatório!");
        }

        var relatorio = new StringBuilder(cabecalho());

        vendas.forEach(venda -> relatorio.append(linha(venda)));
        relatorio.append(rodape(totalizar(vendas)));

        return relatorio.toString();
    }

    protected abstract String cabecalho();

    protected abstract String linha(Venda venda);

    protected String rodape(BigDecimal total) {
        return "";
    }

    private BigDecimal totalizar(List<Venda> vendas) {
        return vendas.stream()
                .map(Venda::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}