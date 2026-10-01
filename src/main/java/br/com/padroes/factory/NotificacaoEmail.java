package br.com.padroes.factory;

public final class NotificacaoEmail implements Notificacao {

    @Override
    public String enviar(String destinatario, String mensagem) {
        if (destinatario == null || !destinatario.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("E-mail de destino inválido!");
        }

        return "[E-mail] para %s: %s".formatted(destinatario, mensagem);
    }
}