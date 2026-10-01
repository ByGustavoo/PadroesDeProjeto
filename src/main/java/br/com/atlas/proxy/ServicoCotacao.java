package br.com.atlas.proxy;

import java.math.BigDecimal;

// Interface comum: o serviço real e o Proxy a implementam, por isso um pode substituir o outro.
public interface ServicoCotacao {

    BigDecimal cotar(String moeda);
}