package br.com.padroes.factory;

public final class NotificacaoPush implements Notificacao {

    @Override
    public String enviar(String destinatario, String mensagem) {
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("Dispositivo de destino inválido!");
        }

        return "[Push] para o dispositivo %s: %s".formatted(destinatario, mensagem);
    }
}