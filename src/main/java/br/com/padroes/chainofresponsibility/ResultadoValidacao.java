package br.com.padroes.chainofresponsibility;

public record ResultadoValidacao(boolean aprovada, String motivo) {

    public static ResultadoValidacao aprovar() {
        return new ResultadoValidacao(true, "Compra aprovada.");
    }

    public static ResultadoValidacao reprovar(String motivo) {
        return new ResultadoValidacao(false, motivo);
    }
}