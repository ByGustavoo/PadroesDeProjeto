package br.com.atlas.factory;

// Produto concreto: valida o telefone e corta mensagens acima do limite de 160 caracteres do SMS.
public final class NotificacaoSms implements Notificacao {

    @Override
    public String enviar(String destinatario, String mensagem) {
        if (destinatario == null || !destinatario.matches("\\+?\\d{10,13}")) {
            throw new IllegalArgumentException("Telefone de destino inválido!");
        }

        var texto = mensagem.length() > 160 ? mensagem.substring(0, 157) + "..." : mensagem;

        return "[SMS] para %s: %s".formatted(destinatario, texto);
    }
}