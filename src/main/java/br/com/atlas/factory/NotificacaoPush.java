package br.com.atlas.factory;

// Produto concreto: envia para o identificador do dispositivo.
public final class NotificacaoPush implements Notificacao {

    @Override
    public String enviar(String destinatario, String mensagem) {
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("Dispositivo de destino inválido!");
        }

        return "[Push] para o dispositivo %s: %s".formatted(destinatario, mensagem);
    }
}