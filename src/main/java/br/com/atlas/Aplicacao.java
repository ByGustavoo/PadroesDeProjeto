package br.com.atlas;

import br.com.atlas.proxy.ExemploProxy;
import br.com.atlas.builder.ExemploBuilder;
import br.com.atlas.factory.ExemploFactory;
import br.com.atlas.observer.ExemploObserver;
import br.com.atlas.strategy.ExemploStrategy;
import br.com.atlas.repository.ExemploRepository;
import br.com.atlas.templatemethod.ExemploTemplateMethod;
import br.com.atlas.chainofresponsibility.ExemploChainOfResponsibility;

import java.util.List;

// Ponto de entrada: executa o exemplo de cada padrão.
public class Aplicacao {

    // main de instância (Java 25): dispensa static e String[] args.
    void main() {
        List<Exemplo> exemplos = List.of(
                new ExemploStrategy(),
                new ExemploBuilder(),
                new ExemploFactory(),
                new ExemploProxy(),
                new ExemploObserver(),
                new ExemploTemplateMethod(),
                new ExemploChainOfResponsibility(),
                new ExemploRepository());

        exemplos.forEach(exemplo -> {
            IO.println();
            IO.println("==== " + exemplo.titulo() + " ====");
            exemplo.executar();
        });
    }
}