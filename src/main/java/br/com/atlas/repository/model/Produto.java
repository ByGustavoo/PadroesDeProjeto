package br.com.atlas.repository.model;

import java.util.UUID;
import java.math.BigDecimal;

// Entidade: tem identidade (id) e protege as próprias regras de negócio.
public class Produto {

    private final UUID id;
    private final String nome;
    private final int estoque;
    private final BigDecimal preco;

    public Produto(String nome, BigDecimal preco, int estoque) {
        // As regras ficam na entidade: um Produto inválido não chega a ser criado.
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

    // Regra de negócio na entidade, e não no mapper nem no service.
    public boolean isDisponivel() {
        return estoque > 0;
    }
}