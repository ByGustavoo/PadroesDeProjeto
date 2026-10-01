package br.com.padroes.proxy;

import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import java.math.BigDecimal;

public class ServicoCotacaoComCache implements ServicoCotacao {

    private final ServicoCotacao servicoReal;
    private final Map<String, BigDecimal> cache = new HashMap<>();

    public ServicoCotacaoComCache(ServicoCotacao servicoReal) {
        this.servicoReal = servicoReal;
    }

    @Override
    public BigDecimal cotar(String moeda) {
        return cache.computeIfAbsent(moeda.trim().toUpperCase(Locale.ROOT), servicoReal::cotar);
    }

    public void limparCache() {
        cache.clear();
    }
}