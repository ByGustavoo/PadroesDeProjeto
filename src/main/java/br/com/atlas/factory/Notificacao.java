package br.com.atlas.factory;

// Produto da Factory: interface comum a todas as notificações. O sealed fixa quais implementações podem existir.
public sealed interface Notificacao permits NotificacaoEmail, NotificacaoSms, NotificacaoPush {

    String enviar(String destinatario, String mensagem);
}