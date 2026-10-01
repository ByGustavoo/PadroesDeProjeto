package br.com.padroes.chainofresponsibility;

import java.math.BigDecimal;

public record Compra(

        String cliente,
        int quantidade,
        BigDecimal valorTotal,
        int estoqueDisponivel,
        BigDecimal limiteCredito

) {}