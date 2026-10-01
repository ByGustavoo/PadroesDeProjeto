package br.com.atlas.chainofresponsibility;

// Resposta da cadeia: aprovada, ou reprovada com o motivo dado pelo elo que barrou a compra.
public record ResultadoValidacao(boolean aprovada, String motivo) {

    public static ResultadoValidacao aprovar() {
        return new ResultadoValidacao(true, "Compra aprovada.");
    }

    public static ResultadoValidacao reprovar(String motivo) {
        return new ResultadoValidacao(false, motivo);
    }
}