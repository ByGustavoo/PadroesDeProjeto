package br.com.atlas.factory;

// Factory: concentra em um só lugar a decisão de qual classe instanciar.
public final class NotificacaoFactory {

    // Construtor privado: a Factory só expõe o método estático de criação.
    private NotificacaoFactory() {
    }

    public static Notificacao criar(TipoNotificacao tipo) {
        // Switch exaustivo: um tipo novo no enum sem case aqui faz o código deixar de compilar.
        return switch (tipo) {
            case EMAIL -> new NotificacaoEmail();
            case SMS -> new NotificacaoSms();
            case PUSH -> new NotificacaoPush();
        };
    }
}