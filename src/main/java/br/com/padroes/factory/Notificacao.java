package br.com.padroes.factory;

public sealed interface Notificacao permits NotificacaoEmail, NotificacaoSms, NotificacaoPush {

    String enviar(String destinatario, String mensagem);
}