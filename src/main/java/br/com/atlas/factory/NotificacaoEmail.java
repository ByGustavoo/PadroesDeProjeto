package br.com.atlas.factory;

// Produto concreto: valida o formato do e-mail antes de enviar.
public final class NotificacaoEmail implements Notificacao {

    @Override
    public String enviar(String destinatario, String mensagem) {
        if (destinatario == null || !destinatario.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("E-mail de destino inválido!");
        }

        return "[E-mail] para %s: %s".formatted(destinatario, mensagem);
    }
}