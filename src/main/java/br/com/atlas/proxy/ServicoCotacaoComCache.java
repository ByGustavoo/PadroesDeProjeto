package br.com.atlas.proxy;

import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import java.math.BigDecimal;

// Proxy: fica na frente do serviço real, com a mesma interface, e adiciona um cache.
public class ServicoCotacaoComCache implements ServicoCotacao {

    // O Proxy guarda uma referência ao objeto real e decide quando delegar a ele.
    private final ServicoCotacao servicoReal;
    private final Map<String, BigDecimal> cache = new HashMap<>();

    public ServicoCotacaoComCache(ServicoCotacao servicoReal) {
        this.servicoReal = servicoReal;
    }

    @Override
    public BigDecimal cotar(String moeda) {
        // Só consulta o serviço real quando a moeda ainda não está no cache.
        return cache.computeIfAbsent(moeda.trim().toUpperCase(Locale.ROOT), servicoReal::cotar);
    }

    // Esvazia o cache e força a próxima cotação a buscar o valor atualizado.
    public void limparCache() {
        cache.clear();
    }
}