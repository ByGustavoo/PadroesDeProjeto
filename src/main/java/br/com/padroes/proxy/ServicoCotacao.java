package br.com.padroes.proxy;

import java.math.BigDecimal;

public interface ServicoCotacao {

    BigDecimal cotar(String moeda);
}