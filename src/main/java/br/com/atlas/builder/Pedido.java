package br.com.atlas.builder;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Produto do Builder: imutável, com todos os campos final, e só o Builder consegue criá-lo.
public final class Pedido {

    private final String cliente;
    private final String observacao;
    private final List<ItemPedido> itens;
    private final String enderecoEntrega;
    private final BigDecimal percentualDesconto;

    // Construtor privado: a única forma de criar um Pedido é passar pelo Builder.
    private Pedido(Builder builder) {
        this.cliente = builder.cliente;
        this.observacao = builder.observacao;
        this.itens = List.copyOf(builder.itens);
        this.enderecoEntrega = builder.enderecoEntrega;
        this.percentualDesconto = builder.percentualDesconto;
    }

    // Ponto de entrada da API fluente: Pedido.builder() ... .build().
    public static Builder builder() {
        return new Builder();
    }

    public String cliente() {
        return cliente;
    }

    // Campo opcional devolvido como Optional, deixando explícito que ele pode não existir.
    public Optional<String> observacao() {
        return Optional.ofNullable(observacao);
    }

    public List<ItemPedido> itens() {
        return itens;
    }

    public String enderecoEntrega() {
        return enderecoEntrega;
    }

    public BigDecimal percentualDesconto() {
        return percentualDesconto;
    }

    public BigDecimal subtotal() {
        return itens.stream()
                .map(ItemPedido::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total() {
        var subtotal = subtotal();
        var desconto = subtotal.multiply(percentualDesconto).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        return subtotal.subtract(desconto).setScale(2, RoundingMode.HALF_UP);
    }

    // Builder: guarda os valores aos poucos, já que nenhum campo precisa ser informado de uma vez.
    public static final class Builder {

        private String cliente;
        private String observacao;
        private String enderecoEntrega;
        private BigDecimal percentualDesconto = BigDecimal.ZERO;
        private final List<ItemPedido> itens = new ArrayList<>();

        private Builder() {
        }

        // Cada método preenche um campo e devolve o próprio Builder, o que permite encadear as chamadas.
        public Builder cliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder enderecoEntrega(String enderecoEntrega) {
            this.enderecoEntrega = enderecoEntrega;
            return this;
        }

        public Builder item(String produto, int quantidade, BigDecimal precoUnitario) {
            this.itens.add(new ItemPedido(produto, quantidade, precoUnitario));
            return this;
        }

        public Builder desconto(BigDecimal percentualDesconto) {
            this.percentualDesconto = percentualDesconto;
            return this;
        }

        public Builder observacao(String observacao) {
            this.observacao = observacao;
            return this;
        }

        // Valida tudo antes de criar o objeto, para que um Pedido nunca exista em estado inválido.
        public Pedido build() {
            if (cliente == null || cliente.isBlank()) {
                throw new IllegalStateException("O cliente do pedido é obrigatório!");
            }

            if (enderecoEntrega == null || enderecoEntrega.isBlank()) {
                throw new IllegalStateException("O endereço de entrega é obrigatório!");
            }

            if (itens.isEmpty()) {
                throw new IllegalStateException("O pedido precisa de pelo menos um item!");
            }

            if (percentualDesconto.signum() < 0 || percentualDesconto.compareTo(new BigDecimal("50")) > 0) {
                throw new IllegalStateException("O desconto deve estar entre 0% e 50%!");
            }

            return new Pedido(this);
        }
    }
}