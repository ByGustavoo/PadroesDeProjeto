package br.com.padroes.repository.model;

import java.util.UUID;
import java.math.BigDecimal;

public class Produto {

    private final UUID id;
    private final String nome;
    private final int estoque;
    private final BigDecimal preco;

    public Produto(String nome, BigDecimal preco, int estoque) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório!");
        }

        if (preco == null || preco.signum() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero!");
        }

        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque do produto não pode ser negativo!");
        }

        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.estoque = estoque;
        this.preco = preco;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean isDisponivel() {
        return estoque > 0;
    }
}