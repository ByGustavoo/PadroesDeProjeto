package br.com.atlas.observer;

import java.math.BigDecimal;

// Evento: os dados que o publicador entrega a cada ouvinte.
public record PedidoCriado(String numero, String cliente, BigDecimal total) {
}