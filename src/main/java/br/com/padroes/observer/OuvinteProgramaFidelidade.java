package br.com.padroes.observer;

import java.util.Map;
import java.util.HashMap;

public class OuvinteProgramaFidelidade implements OuvintePedido {

    private final Map<String, Integer> pontosPorCliente = new HashMap<>();

    @Override
    public void aoCriarPedido(PedidoCriado evento) {
        pontosPorCliente.merge(evento.cliente(), evento.total().intValue(), Integer::sum);
    }

    public int pontosDe(String cliente) {
        return pontosPorCliente.getOrDefault(cliente, 0);
    }
}