package br.com.padroes.templatemethod;

public class RelatorioCsv extends GeradorRelatorio {

    @Override
    protected String cabecalho() {
        return "produto;quantidade;valor_unitario;total\n";
    }

    @Override
    protected String linha(Venda venda) {
        return "%s;%d;%s;%s\n".formatted(venda.produto(), venda.quantidade(), venda.valorUnitario().toPlainString(), venda.total().toPlainString());
    }
}