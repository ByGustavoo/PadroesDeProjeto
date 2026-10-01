package br.com.padroes;

import br.com.padroes.builder.Pedido;
import br.com.padroes.strategy.FretePac;
import br.com.padroes.strategy.FreteSedex;
import br.com.padroes.proxy.ServicoCotacao;
import br.com.padroes.templatemethod.Venda;
import br.com.padroes.observer.PedidoCriado;
import br.com.padroes.factory.NotificacaoSms;
import br.com.padroes.factory.NotificacaoPush;
import br.com.padroes.factory.TipoNotificacao;
import br.com.padroes.factory.NotificacaoEmail;
import br.com.padroes.strategy.CalculadoraFrete;
import br.com.padroes.observer.PublicadorPedidos;
import br.com.padroes.factory.NotificacaoFactory;
import br.com.padroes.observer.OuvinteEnvioEmail;
import br.com.padroes.proxy.ServicoCotacaoRemoto;
import br.com.padroes.templatemethod.RelatorioCsv;
import br.com.padroes.chainofresponsibility.Compra;
import br.com.padroes.strategy.FreteRetiradaNaLoja;
import br.com.padroes.proxy.ServicoCotacaoComCache;
import br.com.padroes.templatemethod.RelatorioTexto;
import br.com.padroes.repository.mapper.ProdutoMapper;
import br.com.padroes.repository.dto.SalvarProdutoDTO;
import br.com.padroes.repository.service.ProdutoService;
import br.com.padroes.observer.OuvinteEmissaoNotaFiscal;
import br.com.padroes.observer.OuvinteProgramaFidelidade;
import br.com.padroes.chainofresponsibility.ValidadorCompra;
import br.com.padroes.repository.ProdutoRepositoryEmMemoria;
import br.com.padroes.chainofresponsibility.ValidadorEstoque;
import br.com.padroes.chainofresponsibility.ValidadorAntifraude;
import br.com.padroes.chainofresponsibility.ValidadorQuantidade;
import br.com.padroes.chainofresponsibility.ValidadorLimiteCredito;
import br.com.padroes.repository.exceptions.ProdutoJaCadastradoException;
import br.com.padroes.repository.exceptions.ProdutoNaoEncontradoException;

import java.util.List;
import java.math.BigDecimal;

public class VerificacaoPadroes {

    private int falhas;
    private int verificacoes;

    void main() {
        verificarStrategy();
        verificarBuilder();
        verificarFactory();
        verificarProxy();
        verificarObserver();
        verificarTemplateMethod();
        verificarChainOfResponsibility();
        verificarRepository();

        IO.println();
        IO.println("%d verificações executadas, %d falha(s).".formatted(verificacoes, falhas));

        if (falhas > 0) {
            System.exit(1);
        }
    }

    private void verificarStrategy() {
        var peso = new BigDecimal("3");
        var distancia = new BigDecimal("120");
        var calculadora = new CalculadoraFrete(new FreteSedex());

        verificar("Strategy: Sedex cobra R$ 34,50", calculadora.calcular(peso, distancia).equals(new BigDecimal("34.50")));

        calculadora.trocarEstrategia(new FretePac());
        verificar("Strategy: PAC cobra R$ 17,60", calculadora.calcular(peso, distancia).equals(new BigDecimal("17.60")));

        calculadora.trocarEstrategia(new FreteRetiradaNaLoja());
        verificar("Strategy: retirada na loja é gratuita", calculadora.calcular(peso, distancia).signum() == 0);

        verificarExcecao("Strategy: peso zero é rejeitado", IllegalArgumentException.class, () -> calculadora.calcular(BigDecimal.ZERO, distancia));
    }

    private void verificarBuilder() {
        var pedido = Pedido.builder()
                .cliente("Ana Souza")
                .enderecoEntrega("Rua das Flores, 120")
                .item("Teclado mecânico", 1, new BigDecimal("349.90"))
                .item("Mouse sem fio", 2, new BigDecimal("89.90"))
                .desconto(new BigDecimal("10"))
                .build();

        verificar("Builder: monta o pedido com os dois itens", pedido.itens().size() == 2);
        verificar("Builder: subtotal de R$ 529,70", pedido.subtotal().equals(new BigDecimal("529.70")));
        verificar("Builder: total com 10% de desconto é R$ 476,73", pedido.total().equals(new BigDecimal("476.73")));
        verificar("Builder: observação opcional fica vazia", pedido.observacao().isEmpty());
        verificarExcecao("Builder: pedido sem cliente é rejeitado", IllegalStateException.class, () -> Pedido.builder().enderecoEntrega("Rua A").item("Cabo", 1, BigDecimal.ONE).build());
        verificarExcecao("Builder: pedido sem itens é rejeitado", IllegalStateException.class, () -> Pedido.builder().cliente("Ana").enderecoEntrega("Rua A").build());
        verificarExcecao("Builder: desconto acima de 50% é rejeitado", IllegalStateException.class, () -> Pedido.builder().cliente("Ana").enderecoEntrega("Rua A").item("Cabo", 1, BigDecimal.ONE).desconto(new BigDecimal("60")).build());
    }

    private void verificarFactory() {
        verificar("Factory: EMAIL cria NotificacaoEmail", NotificacaoFactory.criar(TipoNotificacao.EMAIL) instanceof NotificacaoEmail);
        verificar("Factory: SMS cria NotificacaoSms", NotificacaoFactory.criar(TipoNotificacao.SMS) instanceof NotificacaoSms);
        verificar("Factory: PUSH cria NotificacaoPush", NotificacaoFactory.criar(TipoNotificacao.PUSH) instanceof NotificacaoPush);

        var sms = NotificacaoFactory.criar(TipoNotificacao.SMS).enviar("+5511987654321", "x".repeat(200));

        verificar("Factory: SMS corta mensagens acima de 160 caracteres", sms.endsWith("x".repeat(157) + "..."));
        verificarExcecao("Factory: e-mail inválido é rejeitado", IllegalArgumentException.class, () -> NotificacaoFactory.criar(TipoNotificacao.EMAIL).enviar("ana.souza", "Oi"));
    }

    private void verificarProxy() {
        var servicoRemoto = new ServicoCotacaoRemoto();
        var servicoComCache = new ServicoCotacaoComCache(servicoRemoto);
        ServicoCotacao servico = servicoComCache;

        var primeiraCotacao = servico.cotar("USD");
        servico.cotar("usd");
        servico.cotar("USD");
        servico.cotar("EUR");

        verificar("Proxy: devolve a mesma cotação do serviço real", primeiraCotacao.equals(new BigDecimal("5.42")));
        verificar("Proxy: 4 cotações pedidas geram só 2 consultas remotas", servicoRemoto.consultasRealizadas() == 2);

        servicoComCache.limparCache();
        servico.cotar("USD");

        verificar("Proxy: depois de limpar o cache volta a consultar o serviço real", servicoRemoto.consultasRealizadas() == 3);
        verificarExcecao("Proxy: moeda desconhecida é rejeitada", IllegalArgumentException.class, () -> servico.cotar("XYZ"));
    }

    private void verificarObserver() {
        var publicador = new PublicadorPedidos();
        var envioEmail = new OuvinteEnvioEmail();
        var notaFiscal = new OuvinteEmissaoNotaFiscal();
        var fidelidade = new OuvinteProgramaFidelidade();

        publicador.inscrever(envioEmail);
        publicador.inscrever(notaFiscal);
        publicador.inscrever(fidelidade);
        publicador.publicar(new PedidoCriado("1001", "Ana Souza", new BigDecimal("476.73")));

        verificar("Observer: todos os ouvintes recebem o evento", envioEmail.emailsEnviados().size() == 1 && notaFiscal.notasEmitidas().equals(List.of("NF-1001")) && fidelidade.pontosDe("Ana Souza") == 476);

        publicador.cancelarInscricao(fidelidade);
        publicador.publicar(new PedidoCriado("1002", "Ana Souza", new BigDecimal("100.00")));

        verificar("Observer: ouvinte que cancelou a inscrição não recebe mais eventos", fidelidade.pontosDe("Ana Souza") == 476);
        verificar("Observer: os demais ouvintes continuam recebendo", envioEmail.emailsEnviados().size() == 2 && notaFiscal.notasEmitidas().size() == 2);
    }

    private void verificarTemplateMethod() {
        var vendas = List.of(
                new Venda("Teclado", 3, new BigDecimal("100.00")),
                new Venda("Mouse", 2, new BigDecimal("50.00")));

        var csv = new RelatorioCsv().gerar(vendas);
        var texto = new RelatorioTexto().gerar(vendas);

        verificar("Template Method: CSV tem cabeçalho e uma linha por venda", csv.lines().count() == 3 && csv.contains("Teclado;3;100.00;300.00"));
        verificar("Template Method: CSV usa o rodapé padrão, sem total", !csv.contains("Total"));
        verificar("Template Method: texto sobrescreve o rodapé e mostra o total", texto.contains("Total") && texto.contains("R$ 400,00"));
        verificarExcecao("Template Method: lista vazia é rejeitada pelo algoritmo base", IllegalArgumentException.class, () -> new RelatorioCsv().gerar(List.of()));
    }

    private void verificarChainOfResponsibility() {
        var limite = new BigDecimal("5000");
        var validador = ValidadorCompra.encadear(
                new ValidadorQuantidade(),
                new ValidadorEstoque(),
                new ValidadorLimiteCredito(),
                new ValidadorAntifraude());

        var aprovada = validador.validar(new Compra("Ana", 2, new BigDecimal("700"), 10, limite));
        var semQuantidade = validador.validar(new Compra("Ana", 0, BigDecimal.ZERO, 10, limite));
        var semEstoque = validador.validar(new Compra("Ana", 20, new BigDecimal("700"), 10, limite));
        var semCredito = validador.validar(new Compra("Ana", 2, new BigDecimal("6000"), 10, limite));
        var suspeita = validador.validar(new Compra("Ana", 2, new BigDecimal("12000"), 10, new BigDecimal("20000")));

        verificar("Chain: compra válida passa por todos os elos", aprovada.aprovada());
        verificar("Chain: quantidade zero para no primeiro elo", !semQuantidade.aprovada() && semQuantidade.motivo().contains("quantidade"));
        verificar("Chain: falta de estoque para no elo de estoque", !semEstoque.aprovada() && semEstoque.motivo().contains("Estoque"));
        verificar("Chain: limite estourado para no elo de crédito", !semCredito.aprovada() && semCredito.motivo().contains("crédito"));
        verificar("Chain: valor alto chega ao último elo, o antifraude", !suspeita.aprovada() && suspeita.motivo().contains("análise manual"));
    }

    private void verificarRepository() {
        var produtoService = new ProdutoService(new ProdutoMapper(), new ProdutoRepositoryEmMemoria());

        var teclado = produtoService.salvar(new SalvarProdutoDTO("Teclado", new BigDecimal("349.90"), 5));
        var mouse = produtoService.salvar(new SalvarProdutoDTO("Mouse", new BigDecimal("89.90"), 0));

        verificar("Repository: salvar devolve um DTO com id gerado", teclado.id() != null && teclado.nome().equals("Teclado"));
        verificar("Repository: DTO expõe disponibilidade calculada pela entidade", teclado.disponivel() && !mouse.disponivel());
        verificar("Repository: listar devolve os produtos salvos", produtoService.listar().size() == 2);
        verificar("Repository: buscar encontra pelo id", produtoService.buscar(mouse.id()).nome().equals("Mouse"));
        verificarExcecao("Repository: nome duplicado é rejeitado sem diferenciar maiúsculas", ProdutoJaCadastradoException.class, () -> produtoService.salvar(new SalvarProdutoDTO("TECLADO", BigDecimal.TEN, 1)));
        verificarExcecao("Repository: preço inválido é rejeitado pela entidade", IllegalArgumentException.class, () -> produtoService.salvar(new SalvarProdutoDTO("Cabo", BigDecimal.ZERO, 1)));

        produtoService.deletar(teclado.id());

        verificar("Repository: deletar remove o produto", produtoService.listar().size() == 1);
        verificarExcecao("Repository: buscar produto deletado lança exceção", ProdutoNaoEncontradoException.class, () -> produtoService.buscar(teclado.id()));
    }

    private void verificar(String descricao, boolean condicao) {
        verificacoes++;

        if (condicao) {
            IO.println("[OK]    " + descricao);
            return;
        }

        falhas++;
        IO.println("[FALHA] " + descricao);
    }

    private void verificarExcecao(String descricao, Class<? extends RuntimeException> tipoEsperado, Runnable acao) {
        try {
            acao.run();
            verificar(descricao, false);
        } catch (RuntimeException excecao) {
            verificar(descricao, tipoEsperado.isInstance(excecao));
        }
    }
}