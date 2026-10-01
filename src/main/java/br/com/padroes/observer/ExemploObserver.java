package br.com.padroes.observer;

import br.com.padroes.Exemplo;

import java.math.BigDecimal;

public class ExemploObserver implements Exemplo {

    @Override
    public String titulo() {
        return "Observer";
    }

    @Override
    public void executar() {
        var publicador = new PublicadorPedidos();
        var envioEmail = new OuvinteEnvioEmail();
        var notaFiscal = new OuvinteEmissaoNotaFiscal();
        var fidelidade = new OuvinteProgramaFidelidade();

        publicador.inscrever(envioEmail);
        publicador.inscrever(notaFiscal);
        publicador.inscrever(fidelidade);
        publicador.inscrever(evento -> IO.println("  [log] Pedido " + evento.numero() + " criado para " + evento.cliente()));

        publicador.publicar(new PedidoCriado("1001", "Ana Souza", new BigDecimal("476.73")));
        publicador.publicar(new PedidoCriado("1002", "Bruno Lima", new BigDecimal("129.90")));

        publicador.cancelarInscricao(fidelidade);
        publicador.publicar(new PedidoCriado("1003", "Ana Souza", new BigDecimal("59.90")));

        IO.println("E-mails enviados:");
        envioEmail.emailsEnviados().forEach(email -> IO.println("  " + email));
        IO.println("Notas fiscais emitidas: " + notaFiscal.notasEmitidas());
        IO.println("Pontos de Ana Souza: " + fidelidade.pontosDe("Ana Souza") + " (o pedido 1003 saiu depois que o programa de fidelidade cancelou a inscrição)");
    }
}