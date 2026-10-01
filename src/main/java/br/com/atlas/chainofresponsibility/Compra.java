package br.com.atlas.chainofresponsibility;

import java.math.BigDecimal;

// Requisição que percorre a cadeia de validadores.
public record Compra(

        String cliente,
        int quantidade,
        BigDecimal valorTotal,
        int estoqueDisponivel,
        BigDecimal limiteCredito

) {}