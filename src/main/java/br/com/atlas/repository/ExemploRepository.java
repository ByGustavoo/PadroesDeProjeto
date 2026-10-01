package br.com.atlas.repository;

import br.com.atlas.Moeda;
import br.com.atlas.Exemplo;
import br.com.atlas.repository.mapper.ProdutoMapper;
import br.com.atlas.repository.dto.SalvarProdutoDTO;
import br.com.atlas.repository.service.ProdutoService;
import br.com.atlas.repository.exceptions.ProdutoJaCadastradoException;
import br.com.atlas.repository.exceptions.ProdutoNaoEncontradoException;

import java.math.BigDecimal;

// Composition root: o único lugar que cria as implementações e as conecta.
public class ExemploRepository implements Exemplo {

    @Override
    public String titulo() {
        return "Repository + DTO + Injeção de Dependência";
    }

    @Override
    public void executar() {
        // Sem framework, a injeção é feita à mão: cada peça é criada aqui e entregue ao construtor de quem precisa dela.
        ProdutoRepository produtoRepository = new ProdutoRepositoryEmMemoria();
        var produtoService = new ProdutoService(new ProdutoMapper(), produtoRepository);

        var teclado = produtoService.salvar(new SalvarProdutoDTO("Teclado mecânico", new BigDecimal("349.90"), 15));
        produtoService.salvar(new SalvarProdutoDTO("Mouse sem fio", new BigDecimal("89.90"), 0));
        produtoService.salvar(new SalvarProdutoDTO("Monitor 27\"", new BigDecimal("1899.00"), 4));

        IO.println("Produtos devolvidos pelo service como DTO (a entidade não sai da camada):");
        produtoService.listar().forEach(produto -> IO.println("  %-17s %12s  %s".formatted(produto.nome(), Moeda.formatar(produto.preco()), produto.disponivel() ? "disponível" : "esgotado")));

        IO.println("Busca por id: " + produtoService.buscar(teclado.id()).nome());

        try {
            produtoService.salvar(new SalvarProdutoDTO("teclado MECÂNICO", new BigDecimal("299.90"), 3));
        } catch (ProdutoJaCadastradoException excecao) {
            IO.println("Cadastro duplicado rejeitado: " + excecao.getMessage());
        }

        produtoService.deletar(teclado.id());

        try {
            produtoService.buscar(teclado.id());
        } catch (ProdutoNaoEncontradoException excecao) {
            IO.println("Busca após deletar: " + excecao.getMessage());
        }
    }
}