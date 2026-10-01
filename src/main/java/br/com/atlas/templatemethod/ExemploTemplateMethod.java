package br.com.atlas.templatemethod;

import br.com.atlas.Exemplo;

import java.util.List;
import java.math.BigDecimal;

// Cliente: chama o mesmo gerar() nos dois formatos, e cada um preenche as etapas do seu jeito.
public class ExemploTemplateMethod implements Exemplo {

    @Override
    public String titulo() {
        return "Template Method";
    }

    @Override
    public void executar() {
        var vendas = List.of(
                new Venda("Teclado mecânico", 3, new BigDecimal("349.90")),
                new Venda("Mouse sem fio", 5, new BigDecimal("89.90")),
                new Venda("Monitor 27\"", 1, new BigDecimal("1899.00")));

        IO.println("Relatório em texto (sobrescreve o gancho rodape para mostrar o total):");
        IO.print(new RelatorioTexto().gerar(vendas));

        IO.println();
        IO.println("Relatório em CSV (mantém o rodape padrão, vazio):");
        IO.print(new RelatorioCsv().gerar(vendas));
    }
}