package br.com.padroes.observer;

import java.math.BigDecimal;

public record PedidoCriado(String numero, String cliente, BigDecimal total) {
}