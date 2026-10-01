package br.com.padroes;

import br.com.padroes.proxy.ExemploProxy;
import br.com.padroes.builder.ExemploBuilder;
import br.com.padroes.factory.ExemploFactory;
import br.com.padroes.observer.ExemploObserver;
import br.com.padroes.strategy.ExemploStrategy;
import br.com.padroes.repository.ExemploRepository;
import br.com.padroes.templatemethod.ExemploTemplateMethod;
import br.com.padroes.chainofresponsibility.ExemploChainOfResponsibility;

import java.util.List;

public class Aplicacao {

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