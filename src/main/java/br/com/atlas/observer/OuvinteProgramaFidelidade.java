package br.com.atlas.observer;

import java.util.Map;
import java.util.HashMap;

// Ouvinte concreto: credita ao cliente um ponto por real gasto.
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