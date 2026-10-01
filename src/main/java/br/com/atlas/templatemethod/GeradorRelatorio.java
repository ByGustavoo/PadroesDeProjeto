package br.com.atlas.templatemethod;

import java.util.List;
import java.math.BigDecimal;

// Template Method: a classe abstrata define o esqueleto do algoritmo e deixa algumas etapas para as subclasses.
public abstract class GeradorRelatorio {

    // O método template: final, para que nenhuma subclasse mude a ordem dos passos.
    public final String gerar(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            throw new IllegalArgumentException("Não há vendas para gerar o relatório!");
        }

        var relatorio = new StringBuilder(cabecalho());

        vendas.forEach(venda -> relatorio.append(linha(venda)));
        relatorio.append(rodape(totalizar(vendas)));

        return relatorio.toString();
    }

    // Etapas abstratas: toda subclasse é obrigada a implementá-las.
    protected abstract String cabecalho();

    protected abstract String linha(Venda venda);

    // Gancho (hook): tem uma implementação padrão vazia, e a subclasse só sobrescreve se quiser.
    protected String rodape(BigDecimal total) {
        return "";
    }

    // Passo fixo, compartilhado por todos os formatos.
    private BigDecimal totalizar(List<Venda> vendas) {
        return vendas.stream()
                .map(Venda::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}