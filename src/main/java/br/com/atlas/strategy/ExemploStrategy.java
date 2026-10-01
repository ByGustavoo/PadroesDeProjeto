package br.com.atlas.strategy;

import br.com.atlas.Moeda;
import br.com.atlas.Exemplo;

import java.util.List;
import java.math.BigDecimal;

// Cliente: escolhe as estratégias e as entrega ao contexto.
public class ExemploStrategy implements Exemplo {

    @Override
    public String titulo() {
        return "Strategy";
    }

    @Override
    public void executar() {
        var peso = new BigDecimal("3");
        var distancia = new BigDecimal("120");
        var calculadora = new CalculadoraFrete(new FreteSedex());
        List<EstrategiaFrete> estrategias = List.of(new FreteSedex(), new FretePac(), new FreteRetiradaNaLoja());

        IO.println("Frete de um pacote de 3 kg para 120 km, trocando a estratégia em tempo de execução:");

        // O mesmo contexto produz três resultados diferentes, apenas trocando a estratégia.
        for (var estrategia : estrategias) {
            calculadora.trocarEstrategia(estrategia);
            IO.println("  %-17s %s".formatted(calculadora.nomeDaEstrategia(), Moeda.formatar(calculadora.calcular(peso, distancia))));
        }
    }
}