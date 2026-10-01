package br.com.padroes.factory;

public final class NotificacaoFactory {

    private NotificacaoFactory() {
    }

    public static Notificacao criar(TipoNotificacao tipo) {
        return switch (tipo) {
            case EMAIL -> new NotificacaoEmail();
            case SMS -> new NotificacaoSms();
            case PUSH -> new NotificacaoPush();
        };
    }
}